package com.example.petcare;

import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class petcare extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petcare);


        TextView tvPetFoodLinksContent = findViewById(R.id.tvPetFoodLinksContent);


        String htmlText = "Visit our recommended pet food websites:<br><br>" +
                "<a href='https://www.chewy.com/'>1. Chewy.com</a><br>" +
                "<a href='https://www.petsmart.com/'>2. PetSmart.com</a><br>" +
                "<a href='https://www.petco.com/'>3. Petco.com</a><br>" +
                "<a href='https://www.amazon.com/pets'>4. Amazon_Pets.com</a><br>" +
                "<a href='https://www.wag.com/'>5. Wag.com</a><br>" +
                "<a href='https://www.hillspet.com/'>6. Hill's_Pet_Nutrition.com</a><br>" +
                "<a href='https://www.royalcanin.com/'>7. Royal_Canin.com</a>";


        tvPetFoodLinksContent.setText(Html.fromHtml(htmlText));


        tvPetFoodLinksContent.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
