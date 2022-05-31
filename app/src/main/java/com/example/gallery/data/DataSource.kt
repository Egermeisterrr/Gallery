package com.example.gallery.data

import com.example.gallery.model.Image

class DataSource {
    fun loadImages(): List<Image> {
        return listOf<Image>(
            Image("https://img.labirint.ru/images/upl/descripts/pic_1477575042.jpg"),
            Image("https://im.kommersant.ru/Issues.photo/NEWS/2022/05/26/KMO_162543_33901_1_t222_070023.jpg"),
            Image("https://img.gazeta.ru/files3/458/14919458/GettyImages-1238594697-pic_32ratio_900x600-900x600-52138.jpg"),
            Image("https://www.vokrug.tv/pic/person/a/a/8/6/aa86af9a6cb357f21913d2bac495362d.jpg"),
            Image("https://ichef.bbci.co.uk/news/640/cpsprodpb/CB2C/production/_120221025_gettyimages-2105515.jpg"),
            Image("https://upload.wikimedia.org/wikipedia/commons/6/68/TechCrunch_Disrupt_Europe_Berlin_2013_%2810536888854%29_%28cropped%29.jpg"),
            Image("https://www.occrp.org/images/stories/CCWatch/daily/tinkf.jpg")
        )
    }
}