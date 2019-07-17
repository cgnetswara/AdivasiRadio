package com.example.myapplication;

import java.util.ArrayList;

public class DataUtils {

    public static ArrayList<CardDetail> getArticles() {
        ArrayList<CardDetail> cardDetailArrayList = new ArrayList<>();

        cardDetailArrayList.add(new CardDetail(R.drawable.ic_launcher_background, "राजा की मदद से यहाँ पर बड़ा सा तालाब बना जिसे गोंडी में तड़ाई\n" +
                "कहते हैं", "सीजीनेट जन पत्रकारिता जागरूकता यात्रा आज ग्राम पंचायत-ताडवैली, ब्लॉक-कोयलीबेडा, जिला-उत्तर बस्तर कांकेर (छत्तीसगढ़) में पहुँची है वहां मोहन यादव की मुलाक़ात गाँव के बुज़ुर्ग कन्ना राम वड्डे से हुई है जो उन्हें गोंडी भाषा में उनके ताडवैली गाँव के नाम की कहानी बता रहे हैं कि उनके गाँव का यह नाम कैसे पड़ा: वे बता रहे हैं ये गाँव राजा समय का गाव है परालकोट परगना में एक राजा रहते थे- राजा की मदद से यहाँ पर बड़ा सा तालाब बना जिसे गोंडी में तड़ाई\n" +
                "कहते हैं फिर उसके बाद इसलिए इस गाँव का नाम ताडवैली पड़ा- इसी प्रकार यहां के पेड़ पौधो- व्यक्ति या जानवर आदि पर गाँवों के नाम रखे गए हैं जिनके बारे में गाँव के बुज़ुर्ग ही जानते हैं"));

        cardDetailArrayList.add(new CardDetail(R.mipmap.ic_launcher_round, "नगेबाई जनेबाई और सोनीबाई गोंडी भाषा में एक गीत सुना रहे है",
                "ग्राम-पाड़ेंगा, तहसील-पखांजूर, जिला-उत्तर बस्तर कांकेर (छत्तीसगढ़) से मानकोबाई, गीता उसेण्डी, नगेबाई जनेबाई और सोनीबाई गोंडी भाषा में एक गीत सुना रहे है:\n" +
                        "री-री लोयो री-री लोयो हेला-\n" +
                        "दादा झेला आझी रोय दादा ले-\n" +
                        "री री लोयो री री लो-\n" +
                        "काड़ी इन्जोरे येलो ले-\n" +
                        "मुने दुनियां ता पाटा-\n" +
                        "काड़ी इन्जोरे येलो ले-\n" +
                        "मुने दुनिया ता पाटा-\n" +
                        "आदे पाटा ये ओइ हलेन…"));

        cardDetailArrayList.add(new CardDetail(R.drawable.ic_launcher_background, "कविता कोरचे अपनी सहेलियों के साथ गोंडी भाषा में शादी गीत सुना रहे हैं",
                "ग्राम-तोडरुर, विकासखंड-कोयलीबेड़ा, जिला-उत्तर बस्तर कांकेर (छत्तीसगढ़ ) से कविता कोरचे अपनी सहेलियों के साथ गोंडी भाषा में शादी गीत सुना रहे हैं यह गीत जब लड़का और लड़की को गोटुल के मंडप में नाचते हुए ले जाया जाता है तब गाते हैं:\n" +
                        "रे रे रे ला रे रे रे रेला रे रे लयों रे रे रेला रे रे रे रेला-\n" +
                        "नि वायने गोटुल तमो ले निय वायने गोटुल रो गोटुल तमो ले-\n" +
                        "सिलुड़े पलुड घुती ये सिलुड़े पलुड घुती ये घुती तमो ले-\n" +
                        "गोटुल इमो किकी तमो ले गोटुल इमो किकी रो किकी तमो रे-\n" +
                        "सिलुड़े पलुड घुती ये सिलुड़े पलुड घुती ये घुती तमो ले-\n" +
                        "गोटुल खुदेम आन्दी तमो ले गोटुल खुदेम आन्दी रो आन्दी तमो ले-\n" +
                        "सिलुड़े पलुड घुती ये सिलुड़े पलुड घुती ये घुती तमो ले …"));
        cardDetailArrayList.add(new CardDetail(R.drawable.ic_launcher_foreground, "नागेबाई गोंडी भाषा में बता रही हैं",
                "ग्राम-पाडेनगा, तहसील-पखांजूर, जिला-कांकेर (छत्तीसगढ़) से नागेबाई गोंडी भाषा में बता रही हैं,पहले बस्तर के आदिवासी जंगलो से सब्जी ढूढ कर खाते थे|अभी के आदिवासी हर घर में सब्जी ख़त्म होने से सब्जी के लिए बाजारों में जा कर केमिकल सब्जी ख़रीद" +
                        " कर खा रहे हैं इसलिए अभी के लोगों को जल्दी बीमार पकड़ता हैं,और ज्यादा उम्र तक भी नहीं रह पाते. जंगलो में पाए जाने वाले सब्जिया: बांस की बस्ता,चरोटा बाजी,कोल्यारी बाजी, पहले के आदिवासी ये सब खा के अच्छे रहते थे, लेकिन अब सभी लोगों की खान" +
                        " पान में बदलाव आ गया है.बाजार से लाकर खाते है,पहले के लोग गोबर खाद बनाकर खेतो के लिए इस्तेमाल करते थे,और अभी दुकानों में पाए जाने वाले खाद का इस्तेमाल करते है-जिसके कारण लोग बीमार पड़ जाते है…"));

        cardDetailArrayList.add(new CardDetail(R.drawable.ic_launcher_background, "मोहन यादव की मुलाक़ात गाँव के बुज़ुर्ग कन्ना राम वड्डे से हुई है जो उन्हें गोंडी भाषा में उनके ताडवैली गाँव के नाम की कहानी बता रहे हैं",
                "सीजीनेट जन पत्रकारिता जागरूकता यात्रा आज ग्राम पंचायत-ताडवैली, ब्लॉक-कोयलीबेडा, जिला-उत्तर बस्तर कांकेर (छत्तीसगढ़) में पहुँची है वहां मोहन यादव की मुलाक़ात गाँव के बुज़ुर्ग कन्ना राम वड्डे से हुई है जो उन्हें गोंडी भाषा में उनके ताडवैली गाँव के नाम की कहानी बता रहे हैं कि उनके गाँव का यह नाम कैसे पड़ा: वे बता रहे हैं ये गाँव राजा समय का गाव है परालकोट परगना में एक राजा रहते थे- राजा की मदद से यहाँ पर बड़ा सा तालाब बना जिसे गोंडी में तड़ाई\n" +
                        "कहते हैं फिर उसके बाद इसलिए इस गाँव का नाम ताडवैली पड़ा- इसी प्रकार यहां के पेड़ पौधो- व्यक्ति या जानवर आदि पर गाँवों के नाम रखे गए हैं जिनके बारे में गाँव के बुज़ुर्ग ही जानते हैं"));

        return cardDetailArrayList;

    }

    public static ArrayList<BookPage> getBook() {

        ArrayList<BookPage> bookPages = new ArrayList<BookPage>();

        bookPages.add(new BookPage("गेडा तेबातया वेनेहानो अयता|\n" +
                "बातया वेनेहानो!\n" +
                "\u0010प तुन साफ़ \u0015कयानता|\n" +
                "सफ तु\n" +
                "\u0018ता!\n", R.drawable.page_one));
        bookPages.add(new BookPage("बासके नरला थाना थ\u0018दाना मार ओ अ\u0018ता ,\n" +
                "असके सरोज तीजाइनाता ?\n" +
                "जोगा तीजाइनता!", R.drawable.page_two));
        bookPages.add(new BookPage("अयोय गेडा पा धी| \n उर\u0014-उर!\u0014 उर\u0014-उर\u0014!",
                R.drawable.page_three));
        bookPages.add(new BookPage("असके \u0015गदाला \u0015पटेआनी कोलाया दा \u0015तदाना,\n" +
                "कारना अ\u0018ता असकेभोर तीजाइ\u0018ता तोर?\n" +
                "\u0015व सग हानी पूड\u0015यग \u0015त\u0018तागा!\n", R.drawable.page_four));

        return bookPages;
    }

}
