package com.example.lab2

import java.sql.*
import java.time.LocalDate
import java.io.Serializable;

var url = "http://192.168.0.112:8000/api/v1/";


data class User(
    var id: Int = -1,
    var login: String = "admin",
    var password: String = "admin",
    var fio: String = "МИД",
    var unik: String = "БГУИР",
    var grade: String = "Исит(в ИИ) 3 курс",
    var birthdate: String = "10.02.2002",
) : Serializable

