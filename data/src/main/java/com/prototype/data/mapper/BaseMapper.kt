package com.prototype.data.mapper

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder

// Used to have a simple function to parse identical models within various modules
// Clean Architecture

abstract class BaseMapper {

    inline fun <reified T> parse(src: Any): T {
        with(
            GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
            .disableHtmlEscaping()
            .create()){
            return fromJson(
                toJson(src),
                T::class.java
            )
        }
    }

}