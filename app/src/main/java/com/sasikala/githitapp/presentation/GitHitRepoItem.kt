package com.sasikala.githitapp.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.sasikala.githitapp.data.remote.dto.RePoItem

/**
 * Created by sasikala N on 5/26/24.
 */

@Composable
fun GitHitRepoItem(
    gitHitRepo: RePoItem,
    oncItemClick: (RePoItem) -> Unit
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { oncItemClick(gitHitRepo) }
            .padding(20.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ){
       Column(
           modifier = Modifier
               .fillMaxWidth()
               .weight(1f),
       ){
           Text(text= "${gitHitRepo.full_name}",
               style = MaterialTheme.typography.body1,
               overflow = TextOverflow.Ellipsis
           )

           Text(text= "${gitHitRepo.description}",
               style = MaterialTheme.typography.subtitle1)
       }
        Text(text= "${gitHitRepo.language}",
            fontStyle = FontStyle.Italic,
            textAlign = TextAlign.End,
            style = MaterialTheme.typography.body2,
            )

    }
}