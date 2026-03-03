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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.unimag.sistemadevotacion.R
import com.unimag.sistemadevotacion.data.Candidate
import com.unimag.sistemadevotacion.data.Role
import com.unimag.sistemadevotacion.data.VoteManager
import com.unimag.sistemadevotacion.ui.theme.ToxicGreen
import com.unimag.sistemadevotacion.ui.theme.ToxicGreenDark
import com.unimag.sistemadevotacion.ui.theme.ToxicGreenLight
import kotlinx.coroutines.launch
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDashboardScreen(
    voteManager: VoteManager,
    candidates: List<Candidate>,
    adminViewModel: AdminViewModel = viewModel()
) {
    val uiState by adminViewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var showResetDialog by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = true) {
        adminViewModel.loadResults(voteManager, candidates)
    }

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
                            text = { Text("Reiniciar Votos") },
                            onClick = { 
                                showResetDialog = true
                                showMenu = false 
                            }
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            Surface(
                color = MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "TOTAL PERSONAS QUE HAN VOTADO: ${uiState.totalVotantes}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(12.dp),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }

            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 350.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                items(uiState.roleResults) { roleResult ->
                    RoleSection(result = roleResult)
                }
            }
        }
    }

    // Alerta de confirmación antes de resetear
    if (showResetDialog) {
        AlertDialog(
            onDismissRequest = { showResetDialog = false },
            title = { Text("¡Cuidado!") },
            text = { Text("¿Estás seguro de que quieres borrar todos los votos? Esta acción no se puede deshacer.") },
            confirmButton = {
                Button(
                    onClick = {
                        adminViewModel.resetVotes(voteManager, candidates)
                        showResetDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Text("SÍ, REINICIAR", color = Color.White)
                }
            },
            dismissButton = {
                TextButton(onClick = { showResetDialog = false }) {
                    Text("CANCELAR")
                }
            }
        )
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (result.role == Role.CONTRALOR) "CONTRALOR" else "PERSONERO",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.ExtraBold,
                    color = ToxicGreenDark
                )
                Text(
                    text = "${result.totalVotesInRole} Votos",
                    style = MaterialTheme.typography.titleMedium
                )
            }
            
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
        Modifier.border(2.dp, ToxicGreenDark, RoundedCornerShape(16.dp))
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
                painter = painterResource(id = if (candidate.imageRes != 0) candidate.imageRes else R.mipmap.ic_launcher_foreground),
                contentDescription = null,
                modifier = Modifier
                    .size(72.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                val annotatedText = buildAnnotatedString {
                    append(candidate.name)
                    append(" - ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.ExtraBold, fontSize = 20.sp, color = ToxicGreenDark)) {
                        append("${candidate.votes}")
                    }
                    append(" votos")
                }
                
                Text(text = annotatedText, style = MaterialTheme.typography.titleMedium)
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = "${String.format(Locale.getDefault(), "%.1f", candidate.percentage)}%", 
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
                
                Spacer(modifier = Modifier.height(8.dp))

                Box(modifier = Modifier.fillMaxWidth().height(16.dp).clip(RoundedCornerShape(8.dp)).background(MaterialTheme.colorScheme.surfaceVariant)) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(animatedProgress)
                            .height(16.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(ToxicGreen, ToxicGreenLight)
                                )
                            )
                    )
                }
            }

            if (candidate.isLeader) {
                Box(modifier = Modifier.padding(start = 8.dp)) {
                    Surface(
                        color = ToxicGreen,
                        shape = CircleShape
                    ) {
                        Text(
                            text = "LÍDER",
                            color = Color.Black,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 10.sp,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }
        }
    }
}
