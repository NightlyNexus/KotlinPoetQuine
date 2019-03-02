package com.nightlynexus.kotlinpoetquine

import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.KModifier.CONST
import com.squareup.kotlinpoet.KModifier.PRIVATE
import com.squareup.kotlinpoet.MemberName
import com.squareup.kotlinpoet.ParameterizedTypeName
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.asClassName
import kotlin.String

private const val PACKAGE_NAME: String = "com.nightlynexus.kotlinpoetquine"

private const val FILE_NAME: String = "Quine"

fun main() {
    val string = "%1S"
    val body = """
    |val string = %1S
    |val body = %2S
    |%3T.builder(PACKAGE_NAME, FILE_NAME)
    |    .addProperty(%4T.builder("PACKAGE_NAME", %5T::class, %6M, %7M)
    |        .initializer(string, PACKAGE_NAME).build())
    |    .addProperty(PropertySpec.builder("FILE_NAME", %5T::class, PRIVATE, CONST)
    |        .initializer(string, FILE_NAME).build())
    |    .addFunction(%8T.builder("main")
    |        .addCode(
    |            body,
    |            string,
    |            body,
    |            %3T::class,
    |            %4T::class,
    |            %5T::class,
    |            %10T(%11T::class.%9M(), "PRIVATE"),
    |            %10T(%11T::class.%9M(), "CONST"),
    |            %8T::class,
    |            %10T("com.squareup.kotlinpoet", "asClassName"),
    |            %10T::class,
    |            %11T::class,
    |            %12T::class
    |        )
    |        .build())
    |    .build().writeTo(System.out)
    |""".trimMargin()
    FileSpec.builder(PACKAGE_NAME, FILE_NAME)
        .addProperty(PropertySpec.builder("PACKAGE_NAME", String::class, PRIVATE, CONST)
            .initializer(string, PACKAGE_NAME).build())
        .addProperty(PropertySpec.builder("FILE_NAME", String::class, PRIVATE, CONST)
            .initializer(string, FILE_NAME).build())
        .addFunction(FunSpec.builder("main")
            .addCode(
                body,
                string,
                body,
                FileSpec::class,
                PropertySpec::class,
                String::class,
                MemberName(KModifier::class.asClassName(), "PRIVATE"),
                MemberName(KModifier::class.asClassName(), "CONST"),
                FunSpec::class,
                MemberName("com.squareup.kotlinpoet", "asClassName"),
                MemberName::class,
                KModifier::class,
                ParameterizedTypeName.Companion::class
            )
            .build())
        .build().writeTo(System.out)
}
