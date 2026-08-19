package br.com.fiap.recipes.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import br.com.fiap.recipes.R
import br.com.fiap.recipes.navigation.Destination
import br.com.fiap.recipes.repository.RoomUserRepository
import br.com.fiap.recipes.repository.UserRepository
import br.com.fiap.recipes.ui.theme.RecipesTheme

@Composable
fun LoginScreen(navController: NavHostController) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        TopEndCard(modifier = Modifier.align(Alignment.TopEnd))
        BottomStartCard(modifier = Modifier.align(Alignment.BottomStart))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(alignment = Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,

        ) {
            LoginTitle()
            Spacer(modifier = Modifier.height(32.dp))
            LoginForm(navController)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    RecipesTheme() {
        LoginScreen(rememberNavController())
    }
}

@Composable
fun LoginTitle(modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp)
    ) {
        Text(
            text = stringResource(R.string.login),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = stringResource(R.string.please_sign_in_to_continue),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginTitlePreview() {
    RecipesTheme() {
        LoginTitle()
    }
}

@Composable
fun LoginForm(navController: NavController) {

    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    var showPassword by remember {
        mutableStateOf(false)
    }

    var authenticateError by remember {
        mutableStateOf(false)
    }

    // Criar uma instancia da classe SharedPreferencesUserRepository
    val userRepository : UserRepository =
        RoomUserRepository(context = LocalContext.current)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp)
    ) {
        OutlinedTextField(
            value = email,
            onValueChange = { emailValue ->
                email = emailValue
            },
            modifier = Modifier
                .fillMaxWidth(),
            label = {
                Text(
                    stringResource(R.string.your_e_mail),
                    style = MaterialTheme.typography.labelSmall
                )
            },
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.primary
            ),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = stringResource(R.string.email_icon),
                    tint = MaterialTheme.colorScheme.tertiary
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { passwordValue ->
                password = passwordValue
            },
            modifier = Modifier
                .fillMaxWidth(),
            label = {
                Text(
                    stringResource(R.string.your_password),
                    style = MaterialTheme.typography.labelSmall
                )
            },
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.primary
            ),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = stringResource(R.string.lock_icon),
                    tint = MaterialTheme.colorScheme.tertiary
                )
            },
            trailingIcon = {
                val image = if (showPassword) {
                    Icons.Default.Visibility
                }
                else {
                    Icons.Default.VisibilityOff
                }
                IconButton(
                    onClick = { showPassword = !showPassword }
                ) {
                    Icon(
                    imageVector = image,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.tertiary
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            visualTransformation = if (showPassword) {
                VisualTransformation.None
            }
            else {
                PasswordVisualTransformation()
            }
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(8.dp),
            onClick = {
                val authenticate = userRepository.login(email, password)
                if (authenticate) {
                    navController
                        .navigate(
                            route = Destination.HomeScreen.createRoute(email)
                        )
                }
                else {
                    authenticateError = true
                }
            }
        ) {
            Text(
                text = stringResource(R.string.button_sing_in),
                style = MaterialTheme.typography.labelMedium
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        if (authenticateError) {
            Row() {
                Icon(
                    imageVector = Icons.Default.Error,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.error
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(R.string.authentication_error),
                    color = MaterialTheme.colorScheme.error
                )
            }
        }

        Spacer(modifier = Modifier.padding(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = stringResource(R.string.don_t_have_account_sign_in),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
            TextButton(
                onClick = {
                    navController.navigate(route = Destination.SignupScreen.route)
                },
            ) {
                Text(
                    text = stringResource(R.string.sign_up),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginFormPreview() {
    RecipesTheme() {
        LoginForm(rememberNavController())
    }
}
