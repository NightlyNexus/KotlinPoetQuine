package com.nightlynexus.kotlinpoetquine

import com.squareup.kotlinpoet.ARRAY
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.KModifier.CONST
import com.squareup.kotlinpoet.KModifier.PRIVATE
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.plusParameter
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.asClassName
import kotlin.Array
import kotlin.String

private const val PACKAGE_NAME: String = "com.nightlynexus.kotlinpoetquine"

private const val FILE_NAME: String = "Quine"

fun main(args: Array<String>) {
    val string = "%1S"
    val body = """
    |val string = %1S
    |val body = %2S
    |%3T.builder(PACKAGE_NAME, FILE_NAME)
    |    .addImport("com.squareup.kotlinpoet", "ARRAY")
    |    .addImport("com.squareup.kotlinpoet", "asClassName")
    |    .addImport("com.squareup.kotlinpoet", "ParameterizedTypeName.Companion.plusParameter")
    |    .addImport(%4T::class, "CONST")
    |    .addImport(%4T::class, "PRIVATE")
    |    .addProperty(%5T.builder("PACKAGE_NAME", %6T::class, PRIVATE, CONST)
    |        .initializer(string, PACKAGE_NAME).build())
    |    .addProperty(PropertySpec.builder("FILE_NAME", %6T::class, PRIVATE, CONST)
    |        .initializer(string, FILE_NAME).build())
    |    .addFunction(%7T.builder("main").addParameter(
    |        "args", ARRAY.plusParameter(%6T::class.asClassName()))
    |        .addCode(body, string, body, %3T::class, %4T::class, %5T::class,
    |            %6T::class, %7T::class)
    |        .build())
    |    .build().writeTo(System.out)
    |""".trimMargin()
    FileSpec.builder(PACKAGE_NAME, FILE_NAME)
        .addImport("com.squareup.kotlinpoet", "ARRAY")
        .addImport("com.squareup.kotlinpoet", "asClassName")
        .addImport("com.squareup.kotlinpoet", "ParameterizedTypeName.Companion.plusParameter")
        .addImport(KModifier::class, "CONST")
        .addImport(KModifier::class, "PRIVATE")
        .addProperty(PropertySpec.builder("PACKAGE_NAME", String::class, PRIVATE, CONST)
            .initializer(string, PACKAGE_NAME).build())
        .addProperty(PropertySpec.builder("FILE_NAME", String::class, PRIVATE, CONST)
            .initializer(string, FILE_NAME).build())
        .addFunction(FunSpec.builder("main").addParameter(
            "args", ARRAY.plusParameter(String::class.asClassName()))
            .addCode(body, string, body, FileSpec::class, KModifier::class, PropertySpec::class,
                String::class, FunSpec::class)
            .build())
        .build().writeTo(System.out)
}
