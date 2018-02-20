package com.nightlynexus.kotlinpoetquine

import com.squareup.kotlinpoet.ARRAY
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.KModifier.CONST
import com.squareup.kotlinpoet.KModifier.PRIVATE
import com.squareup.kotlinpoet.ParameterizedTypeName
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.asClassName
import kotlin.Array
import kotlin.String

private const val PACKAGE_NAME: String = "com.nightlynexus.kotlinpoetquine"

private const val FILE_NAME: String = "Quine"

fun main(args: Array<String>) {
    val string = "%S"
    val body = """
    |val string = %S
    |val body = %S
    |%T.builder(PACKAGE_NAME, FILE_NAME)
    |    .addStaticImport("com.squareup.kotlinpoet", "ARRAY")
    |    .addStaticImport("com.squareup.kotlinpoet", "asClassName")
    |    .addStaticImport(%T::class, "CONST")
    |    .addStaticImport(KModifier::class, "PRIVATE")
    |    .addProperty(%T.builder("PACKAGE_NAME", String::class, PRIVATE, CONST)
    |        .initializer(string, PACKAGE_NAME).build())
    |    .addProperty(PropertySpec.builder("FILE_NAME", String::class, PRIVATE, CONST)
    |        .initializer(string, FILE_NAME).build())
    |    .addFunction(%T.builder("main").addParameter(
    |        "args", %T.get(ARRAY, String::class.asClassName()))
    |        .addCode(body, string, body, FileSpec::class, KModifier::class, PropertySpec::class,
    |            FunSpec::class, ParameterizedTypeName::class)
    |        .build())
    |    .build().writeTo(System.out)
    |""".trimMargin()
    FileSpec.builder(PACKAGE_NAME, FILE_NAME)
        .addStaticImport("com.squareup.kotlinpoet", "ARRAY")
        .addStaticImport("com.squareup.kotlinpoet", "asClassName")
        .addStaticImport(KModifier::class, "CONST")
        .addStaticImport(KModifier::class, "PRIVATE")
        .addProperty(PropertySpec.builder("PACKAGE_NAME", String::class, PRIVATE, CONST)
            .initializer(string, PACKAGE_NAME).build())
        .addProperty(PropertySpec.builder("FILE_NAME", String::class, PRIVATE, CONST)
            .initializer(string, FILE_NAME).build())
        .addFunction(FunSpec.builder("main").addParameter(
            "args", ParameterizedTypeName.get(ARRAY, String::class.asClassName()))
            .addCode(body, string, body, FileSpec::class, KModifier::class, PropertySpec::class,
                FunSpec::class, ParameterizedTypeName::class)
            .build())
        .build().writeTo(System.out)
}
