package com.demon.demonnewest.module.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.demon.demonnewest.R
import com.demon.demonnewest.bean.MessageBean
import com.demon.demonnewest.module.compose.ui.theme.DeMonNewestTheme

class ComposeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val list = List(100) {
            MessageBean(
                "Android-$it", "Jetpack Compose,Jetpack Compose,Jetpack Compose,Jetpack Compose,Jetpack Compose,Jetpack Compose,Jetpack Compose,Jetpack Compose,Jetpack Compose"
            )
        }
        setContent {
            DeMonNewestTheme {
                Surface() {
                    Column() {
                        TopAppBar(navigationIcon = {
                            Image(painter = painterResource(R.drawable.abc_vector_test),
                                contentDescription = "back",
                                modifier = Modifier
                                    .clickable {
                                        finish()
                                    }
                                    .padding(16.dp))
                        }, title = {
                            Text(text = "Compose")
                        })
                        Conversation(msgs = list)
                    }
                }
            }
        }
    }

    @Composable
    fun Conversation(msgs: List<MessageBean>) {
        LazyColumn {
            items(msgs) { msg ->
                MessageCard(msg = msg)
            }
        }
    }

    @Composable
    fun MessageCard(msg: MessageBean) {
        Row(modifier = Modifier.padding(all = 8.dp)) {
            Image(
                painter = painterResource(R.drawable.icon_logo),
                contentDescription = "logo",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .border(1.5.dp, MaterialTheme.colors.secondary, CircleShape)
            )
            Spacer(modifier = Modifier.width(8.dp))
            var isExpand by rememberSaveable {
                mutableStateOf(false)
            }
            val surfaceColor by animateColorAsState(
                if (isExpand) MaterialTheme.colors.primary else MaterialTheme.colors.surface
            )
            Column(modifier = Modifier.clickable { isExpand = !isExpand }) {
                Text(
                    text = msg.author, color = MaterialTheme.colors.secondaryVariant, style = MaterialTheme.typography.subtitle1
                )
                Spacer(modifier = Modifier.height(4.dp))
                Surface(
                    shape = MaterialTheme.shapes.medium, elevation = 1.dp, color = surfaceColor, modifier = Modifier
                        .animateContentSize()
                        .padding(1.dp)
                ) {
                    Text(
                        text = msg.body, modifier = Modifier.padding(all = 4.dp), maxLines = if (isExpand) Int.MAX_VALUE else 1, style = MaterialTheme.typography.body2
                    )
                }
            }
        }
    }

}

