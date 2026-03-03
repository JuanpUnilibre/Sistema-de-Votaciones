package com.unimag.sistemadevotacion.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unimag.sistemadevotacion.data.Candidate
import com.unimag.sistemadevotacion.data.Role
import com.unimag.sistemadevotacion.data.VoteManager
import com.unimag.sistemadevotacion.data.VoteState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// --- Data classes para representar el estado de la UI del Dashboard ---

data class AdminDashboardUiState(
    val roleResults: List<RoleResult> = emptyList(),
    val totalVotantes: Int = 0,
    val isLoading: Boolean = true,
    val snackbarMessage: String? = null
)

data class RoleResult(
    val role: Role,
    val candidates: List<CandidateResult>,
    val totalVotesInRole: Int
)

data class CandidateResult(
    val id: Int,
    val name: String,
    val imageRes: Int,
    val votes: Int,
    val percentage: Float,
    val isLeader: Boolean
)

// --- El ViewModel que gestionará la lógica del Dashboard ---

class AdminViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(AdminDashboardUiState())
    val uiState: StateFlow<AdminDashboardUiState> = _uiState.asStateFlow()

    fun loadResults(voteManager: VoteManager, allCandidates: List<Candidate>) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            // Usamos entries en lugar de values() para mayor modernidad en Kotlin
            val resultsByRole = Role.entries.map { role ->
                processRoleResults(role, voteManager, allCandidates)
            }

            _uiState.update {
                it.copy(
                    isLoading = false,
                    roleResults = resultsByRole,
                    totalVotantes = voteManager.getTotalVotantes()
                )
            }
        }
    }

    private fun processRoleResults(role: Role, voteManager: VoteManager, allCandidates: List<Candidate>): RoleResult {
        val candidatesInRole = allCandidates.filter { it.role == role }
        val blankVoteId = if (role == Role.CONTRALOR) VoteState.CONTRALOR_BLANCO_ID else VoteState.PERSONERO_BLANCO_ID
        val blankVotes = voteManager.getVotes(blankVoteId)
        val candidateVotes = candidatesInRole.sumOf { voteManager.getVotes(it.id) }
        val totalVotesInRole = candidateVotes + blankVotes

        val leaderId = candidatesInRole
            .filter { voteManager.getVotes(it.id) > 0 }
            .maxByOrNull { voteManager.getVotes(it.id) }
            ?.let { leader ->
                val leaderVotes = voteManager.getVotes(leader.id)
                // Solo hay líder si no hay empate en el primer puesto
                if (candidatesInRole.count { voteManager.getVotes(it.id) == leaderVotes } == 1) leader.id else null
            }

        val candidateResults = candidatesInRole.map { candidate ->
            val votes = voteManager.getVotes(candidate.id)
            val percentage = if (totalVotesInRole > 0) (votes.toFloat() / totalVotesInRole) * 100f else 0f
            CandidateResult(candidate.id, candidate.name, candidate.imageRes, votes, percentage, candidate.id == leaderId)
        }

        val blankPercentage = if (totalVotesInRole > 0) (blankVotes.toFloat() / totalVotesInRole) * 100f else 0f
        // El voto en blanco se añade como un candidato más para la visualización
        val blankResult = CandidateResult(blankVoteId, "VOTO EN BLANCO", 0, blankVotes, blankPercentage, false)

        return RoleResult(role, candidateResults + blankResult, totalVotesInRole)
    }

    fun refresh(voteManager: VoteManager, allCandidates: List<Candidate>) {
        loadResults(voteManager, allCandidates)
    }

    fun onSnackbarShown() {
        _uiState.update { it.copy(snackbarMessage = null) }
    }

    fun exportToCsv(context: Context, allCandidates: List<Candidate>) {
        viewModelScope.launch {
            val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
            val fileName = "resultados_votacion_$timestamp.csv"
            val file = File(context.filesDir, fileName)

            try {
                file.bufferedWriter().use { out ->
                    out.write("role,candidateId,name,votes,percent,timestamp\n")
                    _uiState.value.roleResults.forEach { roleResult ->
                        roleResult.candidates.forEach { candidate ->
                            // Usamos String.format con Locale explícito para evitar advertencias
                            val formattedPercent = String.format(Locale.getDefault(), "%.2f", candidate.percentage)
                            val line = "${roleResult.role},${candidate.id},${candidate.name.replace(",", "")},${candidate.votes},$formattedPercent,$timestamp\n"
                            out.write(line)
                        }
                    }
                }
                _uiState.update { it.copy(snackbarMessage = "Exportado con éxito a $fileName") }
            } catch (e: Exception) {
                _uiState.update { it.copy(snackbarMessage = "Error al exportar: ${e.message}") }
            }
        }
    }

    fun resetVotes(voteManager: VoteManager, allCandidates: List<Candidate>) {
        viewModelScope.launch {
            val ids = allCandidates.map { it.id } + listOf(VoteState.CONTRALOR_BLANCO_ID, VoteState.PERSONERO_BLANCO_ID)
            voteManager.resetAll(ids)
            refresh(voteManager, allCandidates)
            _uiState.update { it.copy(snackbarMessage = "Votos reiniciados correctamente") }
        }
    }
}
