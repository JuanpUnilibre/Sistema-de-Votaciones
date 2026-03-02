package com.unimag.sistemadevotacion.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.unimag.sistemadevotacion.R
import com.unimag.sistemadevotacion.data.Candidate
import com.unimag.sistemadevotacion.data.Role
import com.unimag.sistemadevotacion.data.VoteManager
import com.unimag.sistemadevotacion.ui.theme.PrimaryRed
import com.unimag.sistemadevotacion.ui.theme.SecondaryYellow
import com.unimag.sistemadevotacion.ui.theme.SuccessGreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDashboardScreen(
    voteManager: VoteManager,
    candidates: List<Candidate>,
    adminViewModel: AdminViewModel = viewModel()
) {
    val uiState by adminViewModel.uiState.collectAsState()
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        adminViewModel.loadResults(voteManager, candidates)
    }

    // Observa los mensajes de Snackbar del ViewModel
    LaunchedEffect(uiState.snackbarMessage) {
        uiState.snackbarMessage?.let {
            scope.launch {
                snackbarHostState.showSnackbar(it)
                adminViewModel.onSnackbarShown()
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Resultados - Dashboard") },
                actions = {
                    var showMenu by remember { mutableStateOf(false) }
                    IconButton(onClick = { showMenu = true }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "Más opciones")
                    }
                    DropdownMenu(expanded = showMenu, onDismissRequest = { showMenu = false }) {
                        DropdownMenuItem(
                            text = { Text("Actualizar") },
                            onClick = {
                                adminViewModel.refresh(voteManager, candidates)
                                showMenu = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Exportar CSV") },
                            onClick = {
                                adminViewModel.exportToCsv(context, candidates)
                                showMenu = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Reiniciar Votos") },
                            onClick = { /* TODO */ }
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            )
        }
    ) { paddingValues ->
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 350.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(8.dp)
        ) {
            items(uiState.roleResults) { roleResult ->
                RoleSection(result = roleResult)
            }
        }
    }
}

@Composable
fun RoleSection(result: RoleResult) {
    Card(
        modifier = Modifier.padding(8.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = if (result.role == Role.CONTRALOR) "CONTRALOR" else "PERSONERO",
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = "${result.totalVotesInRole} Votos Totales",
                style = MaterialTheme.typography.labelMedium
            )
            Spacer(modifier = Modifier.height(16.dp))

            result.candidates.forEach {
                CandidateCard(candidate = it)
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
fun CandidateCard(candidate: CandidateResult) {
    val animatedProgress by animateFloatAsState(
        targetValue = candidate.percentage / 100f,
        animationSpec = tween(durationMillis = 800),
        label = "progressAnimation"
    )

    val borderModifier = if (candidate.isLeader) {
        Modifier.border(2.dp, SuccessGreen, RoundedCornerShape(16.dp))
    } else {
        Modifier
    }

    Card(
        modifier = Modifier.fillMaxWidth().then(borderModifier),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = if (candidate.imageRes != 0) candidate.imageRes else R.drawable.ic_school_logo),
                contentDescription = "Imagen de ${candidate.name}",
                modifier = Modifier
                    .size(72.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(candidate.name, style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(4.dp))
                Text("${candidate.votes} Votos (${String.format("%.1f", candidate.percentage)}%)", style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(8.dp))

                // Barra de Progreso
                Box(modifier = Modifier.fillMaxWidth().height(20.dp).clip(RoundedCornerShape(10.dp)).background(MaterialTheme.colorScheme.surfaceVariant)) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(animatedProgress)
                            .height(20.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(PrimaryRed, SecondaryYellow)
                                )
                            )
                    )
                }
            }

            if (candidate.isLeader) {
                Box(modifier = Modifier.padding(start = 8.dp)) {
                    Text(
                        text = "LÍDER",
                        color = SuccessGreen,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 12.sp,
                        modifier = Modifier
                            .background(SuccessGreen.copy(alpha = 0.1f), CircleShape)
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
        }
    }
}