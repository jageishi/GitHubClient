package org.ageage.githubclient.core.ui.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import org.ageage.githubclient.core.ui.R
import org.ageage.githubclient.core.ui.util.ApiErrorState

@Composable
fun ApiErrorDialog(
    apiErrorState: ApiErrorState,
    onDismissRequest: () -> Unit
) {
    if (apiErrorState == ApiErrorState.None) return

    AlertDialog(
        onDismissRequest = { onDismissRequest() },
        title = {
            Text(
                text = stringResource(R.string.error),
                style = MaterialTheme.typography.titleLarge,
            )
        },
        text = {
            Text(
                text =
                when (apiErrorState) {
                    ApiErrorState.NetworkError -> stringResource(id = R.string.error_offline)
                    ApiErrorState.ForbiddenError -> stringResource(id = R.string.error_forbidden)
                    ApiErrorState.ServerError -> stringResource(id = R.string.error_server)
                    ApiErrorState.UnexpectedError -> stringResource(id = R.string.error_unexpected)
                    ApiErrorState.None -> ""
                }
            )

        },
        confirmButton = {
            TextButton(
                onClick = { onDismissRequest() }
            ) {
                Text(
                    text = stringResource(id = R.string.ok),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    )
}

@Preview
@Composable
private fun Preview_ApiErrorDialog_NetworkError() {
    ApiErrorDialog(
        apiErrorState = ApiErrorState.NetworkError,
        onDismissRequest = {}
    )
}

@Preview
@Composable
private fun Preview_ApiErrorDialog_ForbiddenError() {
    ApiErrorDialog(
        apiErrorState = ApiErrorState.ForbiddenError,
        onDismissRequest = {}
    )
}

@Preview
@Composable
private fun Preview_ApiErrorDialog_ServerError() {
    ApiErrorDialog(
        apiErrorState = ApiErrorState.ServerError,
        onDismissRequest = {}
    )
}

@Preview
@Composable
private fun Preview_ApiErrorDialog_UnexpectedError() {
    ApiErrorDialog(
        apiErrorState = ApiErrorState.UnexpectedError,
        onDismissRequest = {}
    )
}

@Preview
@Composable
private fun Preview_ApiErrorDialog_None() {
    ApiErrorDialog(
        apiErrorState = ApiErrorState.None,
        onDismissRequest = {}
    )
}