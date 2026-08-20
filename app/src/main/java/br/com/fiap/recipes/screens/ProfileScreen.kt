package br.com.fiap.recipes.screens

import android.database.sqlite.SQLiteConstraintException
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Patterns
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.com.fiap.recipes.R
import br.com.fiap.recipes.model.User
import br.com.fiap.recipes.navigation.Destination
import br.com.fiap.recipes.repository.RoomUserRepository
import br.com.fiap.recipes.ui.theme.RecipesTheme
import br.com.fiap.recipes.utils.convertBitmapToByteArray
import br.com.fiap.recipes.utils.convertByteArrayToBitmap

@Composable
fun ProfileScreen(navController: NavController, email: String?) {

    val context = LocalContext.current
    val userRepository = remember { RoomUserRepository(context) }
    val user = remember(email) { email?.let { userRepository.getUserByEmail(it) } }

    // Criar uma variável que armazenará a imagem padrão do perfil do usuário
    val placeHolderImage = remember {
        BitmapFactory.decodeResource(
            context.resources,
            android.R.drawable.ic_menu_gallery
        )
    }

    // Armazenar a imagem de profile do usuário em uma variável Bitmap
    var profileImage by remember(user) {
        mutableStateOf<Bitmap>(
            user?.userImage?.let { convertByteArrayToBitmap(it) } ?: placeHolderImage
        )
    }

    // Criar um lançador de atividade para selecionar Imagem da galeria
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            if (Build.VERSION.SDK_INT < 28) {
                profileImage = MediaStore.Images.Media.getBitmap(context.contentResolver, it)
            } else {
                val source = ImageDecoder.createSource(context.contentResolver, it)
                profileImage = ImageDecoder.decodeBitmap(source)
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                MaterialTheme.colorScheme.background
            )
    ) {
        TopEndCard(modifier = Modifier.align(Alignment.TopEnd))
        BottomStartCard(modifier = Modifier.align(Alignment.BottomStart))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(alignment = Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ProfileTitleComponent()
            Spacer(modifier = Modifier.height(48.dp))
            ProfileUserImage(profileImage, launcher)
            ProfileUserForm(navController, user, profileImage)
        }
    }
}

@Preview
@Composable
private fun ProfileScreenPreview() {
    RecipesTheme {
        ProfileScreen(navController = rememberNavController(), email = "")
    }
}

@Composable
fun ProfileTitleComponent(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.profile),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleLarge

        )
        Text(
            text = stringResource(R.string.user_profile_details),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleSmall
        )
    }
}

@Composable
fun ProfileUserImage(profileImage: Bitmap, launcher: ManagedActivityResultLauncher<String, Uri?>) {
    Box(
        modifier = Modifier
            .size(120.dp)
    ) {
        Image(
            bitmap = profileImage.asImageBitmap(),
            contentDescription = stringResource(R.string.user_image),
            modifier = Modifier
                .clip(shape = CircleShape)
                .size(100.dp)
                .align(alignment = Alignment.Center)
        )
        Icon(
            imageVector = Icons.Default.AddAPhoto,
            contentDescription = stringResource(R.string.add_photo_icon),
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .align(alignment = Alignment.BottomEnd)
                .clickable(
                    onClick = {
                        launcher.launch("image/*")
                    }
                )
        )
    }
}

@Composable
fun ProfileUserForm(navController: NavController, user: User?, profileImage: Bitmap) {

    var showDeleteDialog by remember {
        mutableStateOf(false)
    }

    // Device explorer packages - cache, code_cache, files and shared_prefs
    // Instancia do SharedPreferencesUserRepository : Injeção de dependencia?
    val userRepository = RoomUserRepository(context = LocalContext.current)

    var name by remember(user) { mutableStateOf(user?.name ?: "") }
    var email by remember(user) { mutableStateOf(user?.email ?: "") }
    var password by remember(user) { mutableStateOf(user?.password ?: "") }

    // Variaveis de estado para verificar se os dados estão corretos
    var isNameError by remember { mutableStateOf(false) }
    var isEmailError by remember { mutableStateOf(false) }
    var isPasswordError by remember { mutableStateOf(false) }

    // variavel de estado para controlar mensagem de Sucesso!!!!
    var showDialogSuccess by remember { mutableStateOf(false) }

    // Variavel de estado para controlar a exibição da mensagem de erro
    var showDialogError by remember { mutableStateOf(false) }


    // Função para verificar se os dados estão corretos:
    // Essa função apenas retornara se os campos forem verdadeiros!!
    fun validate(): Boolean {
        isNameError = name.length < 3
        isEmailError = email.length < 3 || !Patterns.EMAIL_ADDRESS.matcher(email).matches()
        isPasswordError = password.length < 3

        return !isNameError && !isEmailError && !isPasswordError
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp)
    ) {
        // Caixa de texto para nome do usuário
        OutlinedTextField(
            value = name,
            onValueChange = {
                name = it
            },
            modifier = Modifier
                .fillMaxWidth(),
            label = {
                Text(
                    text = stringResource(R.string.your_name),
                    style = MaterialTheme.typography.labelSmall
                )
            },
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.primary,
            ),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = stringResource(R.string.person_icon),
                    tint = MaterialTheme.colorScheme.tertiary
                )
            },
            isError = isNameError,
            trailingIcon = {
                if (isNameError) {
                    Icon(
                        imageVector = Icons.Default.Error,
                        contentDescription = "Error Icon",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            },
            supportingText = {
                if (isNameError) {
                    Text(
                        text = "Name must have at least 3 characters",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.End
                    )
                }
            }
        )

        // Caixa de texto para email
        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
            },
            modifier = Modifier
                .fillMaxWidth(),
            label = {
                Text(
                    text = stringResource(R.string.your_e_mail),
                    style = MaterialTheme.typography.labelSmall
                )
            },
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.primary,
            ),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = stringResource(R.string.email_icon),
                    tint = MaterialTheme.colorScheme.tertiary
                )
            },

            isError = isEmailError,
            trailingIcon = {
                if (isEmailError) {
                    Icon(
                        imageVector = Icons.Default.Error,
                        contentDescription = "Error Icon",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            },
            supportingText = {
                if (isEmailError) {
                    Text(
                        text = "Email invalid",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.End
                    )
                }
            }
        )

        // Caixa de texto para a senha
        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
            },
            modifier = Modifier
                .fillMaxWidth(),
            label = {
                Text(
                    text = stringResource(R.string.your_password),
                    style = MaterialTheme.typography.labelSmall
                )
            },
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.primary,
            ),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = stringResource(R.string.lock_icon),
                    tint = MaterialTheme.colorScheme.tertiary
                )
            },

//            trailingIcon = {
//                Icon(
//                    imageVector = Icons.Default.RemoveRedEye,
//                    contentDescription = stringResource(R.string.eye_icon),
//                    tint = MaterialTheme.colorScheme.tertiary,
//                )
//            },

            isError = isPasswordError,
            trailingIcon = {
                if (isPasswordError) {
                    Icon(
                        imageVector = Icons.Default.Error,
                        contentDescription = "Error Icon",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            },
            supportingText = {
                if (isPasswordError) {
                    Text(
                        text = "Password invalid",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.End
                    )
                }
            }

        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                if (validate()) {
                    val updatedUser = User(
                        id = user?.id ?: 0,
                        name = name,
                        email = email,
                        password = password,
                        userImage = convertBitmapToByteArray(profileImage)
                    )
                    try {
                        userRepository.updateUser(updatedUser)
                        showDialogSuccess = true
                    }
                    catch (e: SQLiteConstraintException) {
                        isEmailError = true
                        showDialogError = true
                    }
                }
                else {
                    showDialogError = true
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = stringResource(R.string.update_profile),
                style = MaterialTheme.typography.labelMedium
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botao para deletar
        Button(
            onClick = {
                showDeleteDialog = true
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.tertiary
            )
        ) {
            Text(
                text = "Delete profile",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onTertiary
            )
        }

        if (showDeleteDialog != false) {
            AlertDialog(
                onDismissRequest = { showDeleteDialog = false },
                title = {
                    Text(
                        text = "User removal"
                    )
                },
                text = {
                    Text(
                        text = "Are you sure you want to delete your account?"
                    )
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            showDeleteDialog = false
                            if (user != null) {
                                userRepository.delete(user)
                                navController.navigate(Destination.LoginScreen.route)
                            }
                        }
                    ) {
                        Text(text = "OK")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            showDeleteDialog = false
                        }
                    ) {
                        Text(text = "Cancel")
                    }
                }
            )
        }



        // Caixa de dialogo de sucesso
        if (showDialogSuccess) {
            AlertDialog(
                onDismissRequest = { showDialogSuccess = false },
                title = {
                    Text(text = "Success")
                },
                text = {
                    Text(text = "Account updated successfully")
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            navController.navigate(Destination.LoginScreen.route)
                        }
                    ) {
                        Text(text = "OK")
                    }
                }
            )
        }


        // Caixa de dialogo de erro
        if (showDialogError) {
            AlertDialog(
                onDismissRequest = { showDialogError = false },
                title = {
                    Text(text = "Error")
                },
                text = {
                    Text(text = "Please fill in all fields correctly")
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            showDialogError = false
                        }
                    ) {
                        Text(text = "OK")
                    }
                }
            )
        }

    }
}
