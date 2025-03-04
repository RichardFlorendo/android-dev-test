package com.example.modulus_labs_dev_test.view.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.modulus_labs_dev_test.api.model.PokemonResponse
import com.example.modulus_labs_dev_test.api.model.PokemonResult
import com.example.modulus_labs_dev_test.viewmodel.MainViewModel

@Composable
fun PokemonScreen(modifier: Modifier = Modifier,
                 viewState: MainViewModel.PokemonState,
                 navigateToDetail: (PokemonResult) -> Unit){

    Box (modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)){
        when{//Checks if loading
            viewState.loading ->{ //Loading
                CircularProgressIndicator(modifier.align(Alignment.Center))
            }
            viewState.error != null ->{ //Not Loading with Error
                Log.d("RESPONSE3", viewState.error.toString())
                Text(text = "ERROR OCCURRED")
            }
            else->{
                //Display Pokemon when not loading and no error
                PokemonScreen(pokemonList = viewState.list, navigateToDetail)
            }

        }
    }
}

@Composable
fun PokemonScreen(
    pokemonList: List<PokemonResult>,
    navigateToDetail: (PokemonResult) -> Unit){

    var searchText by remember { mutableStateOf("") }

    Column (Modifier
            .padding(16.dp)
            .fillMaxSize()
    ){
        Row (Modifier
            .fillMaxWidth()){

            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                label = { Text(text = "Search Pokemon Name") }
            )

            Button(onClick = {},
                enabled = false){
                Text(text = "Search")
                Icon(Icons.Default.Search,
                    contentDescription = "Search Button")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(1), //displays items in a column with specific column numbers
        ) {
            items(pokemonList) {//pass each item through the PokemonItem function
                    pokemon -> //passes retrieved data to the function
                PokemonItem(pokemon = pokemon, navigateToDetail)
            }
        }
    }
}

//How each item should look like
@Composable
fun PokemonItem(
    pokemon: PokemonResult,
    navigateToDetail: (PokemonResult) -> Unit){
    Column(modifier = Modifier
        .padding(8.dp)
        .fillMaxSize()
        .clickable { navigateToDetail(pokemon) }, //allows the item to be clickable
        horizontalAlignment = Alignment.CenterHorizontally)
    {
//        Image(painter = rememberAsyncImagePainter(model = pokemon.sprites), //temp
//            //loads image with one line of code, from io implementation in build.gradle
//            contentDescription = null,
//            modifier = Modifier
//                .fillMaxSize()
//                .aspectRatio(1f))

        Text(text = pokemon.name, //temp
            color = Color.Black,
            style = TextStyle(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}