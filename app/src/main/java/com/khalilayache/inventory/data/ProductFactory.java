package com.khalilayache.inventory.data;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

import android.content.Context;

import com.khalilayache.inventory.R;
import com.khalilayache.inventory.model.Product;
import com.khalilayache.inventory.utils.AndroidUtils;

public class ProductFactory {


  public static ArrayList<Product> createProductList(Context context) {
    ArrayList<Product> productsList = new ArrayList<>();

    productsList.add(new Product(
        "Notebook Samsung",
        "Ultrabook Samsung 530U3C-AD3 dark grey processor Intel® Core i5 2537M",
        getRandomPrice(2500),
        getRandomQuantity(30),
        String.valueOf(AndroidUtils.getUriStringOfDrawable(R.drawable.notebook, context)),
        "Magazine Luisa",
        "vendas@magazineluisa.com.br",
        "11 1234–5678"
    ));

    productsList.add(new Product(
        "Câmera Digital Nikon D5200",
        "A Nikon D5200 é exatamente esse tipo de câmera: uma HD-SLR excepcional.",
        getRandomPrice(3000),
        getRandomQuantity(30),
        String.valueOf(AndroidUtils.getUriStringOfDrawable(R.drawable.camera_d5200, context)),
        "Casas Bahia",
        "vendas@casasbahia.com.br",
        "11 9875–4325"
    ));

    productsList.add(new Product(
        "Playstation 4",
        "Console de videogame produzido pela ony Interactive Entertainment.",
        getRandomPrice(2000),
        getRandomQuantity(200),
        String.valueOf(AndroidUtils.getUriStringOfDrawable(R.drawable.playstation, context)),
        "Lojas Americanas",
        "vendas@americanas.com.br",
        "11 9935-4845"
    ));

    productsList.add(new Product(
        "Power Bank Inova 10000mAh",
        "Excelente carregador portátil para uso diário, com boa capacidade de carga.",
        getRandomPrice(80),
        getRandomQuantity(60),
        String.valueOf(AndroidUtils.getUriStringOfDrawable(R.drawable.power_bank, context)),
        "Ponto Frio",
        "vendas@pontofrio.com.br",
        "11 6741–7584"
    ));

    productsList.add(new Product(
        "Fone de Ouvido Philips She1350bk",
        "Com reforço dinâmico de graves, este fone de ouvido intra-auricular leva música de qualidade aos seus ouvidos confortavelmente.",
        getRandomPrice(20),
        getRandomQuantity(20),
        String.valueOf(AndroidUtils.getUriStringOfDrawable(R.drawable.fone_ouvido, context)),
        "Saraiva",
        "vendas@saraiva.com.br",
        "21 5814-5766"
    ));

    productsList.add(new Product(
        "Lanterna Mini Maglite Preta M2A01H",
        "Benefícios que a tornaram famosa no mundo todo: design, qualidade, durabilidade e portabilidade.",
        getRandomPrice(200),
        getRandomQuantity(15),
        String.valueOf(AndroidUtils.getUriStringOfDrawable(R.drawable.lanterna, context)),
        "Lojas Americanas",
        "vendas@americanas.com.br",
        "11 9935-4845"
    ));

    productsList.add(new Product(
        "Caixa de Som Bluetooth Portatil JBL Charge 3",
        "A Caixa Bluetooth à prova d ́agua Charge 3 agora vem com carregador portátil e deixa o som ainda mais potente.",
        getRandomPrice(500),
        getRandomQuantity(30),
        String.valueOf(AndroidUtils.getUriStringOfDrawable(R.drawable.caixa_som, context)),
        "Amazon",
        "vendas@amazon.com.br",
        "11 7627-2815"
    ));

    productsList.add(new Product(
        "Samsung Galaxy S8",
        "Com o Galaxy S8, tudo é ainda mais incrível. Desde a câmera até o sistema de reconhecimento de íris, que deixa o aparelho muito " +
            "mais seguro.",
        getRandomPrice(3500),
        getRandomQuantity(30),
        String.valueOf(AndroidUtils.getUriStringOfDrawable(R.drawable.samsung_s8, context)),
        "Lojas Americanas",
        "vendas@americanas.com.br",
        "11 9935-4845"
    ));

    productsList.add(new Product(
        "Cafeteira Expresso 19 Bar Nespresso Inissia",
        "Controle Automático do Volume do café;Bomba de Alta pressão;Preparo de Expresso e Lungo ao toque de um botão.",
        getRandomPrice(300),
        getRandomQuantity(50),
        String.valueOf(AndroidUtils.getUriStringOfDrawable(R.drawable.nespresso, context)),
        "Magazine Luiza",
        "vendas@magazineluiza.com.br",
        "11 1234-5678"
    ));

    return productsList;
  }

  private static int getRandomQuantity(int max) {
    return new Random().nextInt(max);
  }

  private static double getRandomPrice(int max) {
    double price = (1 + (max - 1) * new Random().nextDouble());
    String priceString = String.format(Locale.getDefault(), "%.2f", price);
    priceString = priceString.replace(",", ".");
    return Double.parseDouble(priceString);
  }
}
