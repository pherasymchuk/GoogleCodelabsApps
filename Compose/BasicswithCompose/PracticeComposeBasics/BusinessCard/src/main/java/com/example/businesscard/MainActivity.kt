package com.example.businesscard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.businesscard.ui.theme.BasicsWithComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BasicsWithComposeTheme {
                // A surface container using the 'background' color from the theme
                Scaffold { innerPadding ->
                    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                        BusinessCard(Modifier.fillMaxSize(), innerPadding)
                    }
                }
            }
        }
    }
}

@Composable
fun BusinessCard(modifier: Modifier = Modifier, innerPadding: PaddingValues) {
    val backgroundColor = Color(0xffd2e8d4)
    Column(
        modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(innerPadding)
    ) {
        LogoDetails(
            fullName = "Pavlo Herasymchuk",
            title = "Android Developer",
            Modifier
                .fillMaxSize()
                .weight(1f)
        )
        ContactInformation(
            phone = "+380666666666",
            socialMedia = "t.me/pavlo_herasymchuk",
            email = "pavlo.herasymchuk@gmail.com",
            Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
fun LogoDetails(fullName: String, title: String, modifier: Modifier = Modifier) {
    val logo = painterResource(id = R.drawable.android_logo)
    val logoBackgroundColor = Color(0xFF073042)

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = logo,
            contentDescription = "Android logo",
            Modifier
                .size(100.dp)
                .background(logoBackgroundColor)
        )
        Text(
            text = fullName,
            fontSize = 28.sp,
            modifier = Modifier.padding(top = 4.dp)
        )
        Text(
            text = title,
            color = Color(0xFF197849),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@Composable
fun ContactInformation(
    phone: String,
    socialMedia: String,
    email: String,
    modifier: Modifier = Modifier
) {
    val phoneIcon: Painter = painterResource(id = R.drawable.ic_phone_number)
    val socialMediaIcon: Painter = painterResource(id = R.drawable.ic_social_media)
    val emailIcon: Painter = painterResource(id = R.drawable.ic_email)

    Box(modifier = modifier) {
        Column(verticalArrangement = Arrangement.Bottom) {
            Row(modifier = Modifier.padding(bottom = 4.dp)) {
                Image(painter = phoneIcon, contentDescription = null, Modifier.padding(end = 8.dp))
                Text(text = phone)
            }
            Row(modifier = Modifier.padding(bottom = 4.dp)) {
                Image(painter = socialMediaIcon, contentDescription = null, Modifier.padding(end = 8.dp))
                Text(text = socialMedia)
            }
            Row(modifier = Modifier.padding(bottom = 4.dp)) {
                Image(painter = emailIcon, contentDescription = null, Modifier.padding(end = 8.dp))
                Text(text = email)
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BusinessCardPreview() {
    BasicsWithComposeTheme {
        BusinessCard(innerPadding = PaddingValues(0.dp))
    }
}

@Preview
@Composable
private fun ContactInformationPreview() {
    ContactInformation(phone = "+00 (00) 000 000", socialMedia = "socialmedia", email = "example@gmail.com")
}
