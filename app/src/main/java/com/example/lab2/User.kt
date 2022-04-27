package com.example.lab2

import java.time.LocalDate
import java.io.Serializable;


data class User(
    var id: Int = -1,
    var login: String = "max",
    var password: String = "max",
    var FIO: String = "MMA",
    var Unik: String = "БГУИР",
    var Grade: String = "Исит(в ИИ) 3 курс",
    var BirthDate: LocalDate = LocalDate.of( 2002, 5 , 28 ),
) : Serializable

