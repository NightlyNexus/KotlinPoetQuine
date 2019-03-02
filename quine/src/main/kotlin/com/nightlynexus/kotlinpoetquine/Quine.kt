package com.nightlynexus.kotlinpoetquine

import com.squareup.kotlinpoet.ARRAY
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.KModifier.CONST
import com.squareup.kotlinpoet.KModifier.PRIVATE
import com.squareup.kotlinpoet.MemberName
import com.squareup.kotlinpoet.ParameterizedTypeName
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
    |    .addProperty(%4T.builder("PACKAGE_NAME", %5T::class, %6M, %7M)
    |        .initializer(string, PACKAGE_NAME).build())
    |    .addProperty(PropertySpec.builder("FILE_NAME", %5T::class, PRIVATE, CONST)
    |        .initializer(string, FILE_NAME).build())
    |    .addFunction(%8T.builder("main").addParameter(
    |        "args", %9M.%10M(%5T::class.%11M()))
    |        .addCode(
    |            body,
    |            string,
    |            body,
    |            %3T::class,
    |            %4T::class,
    |            %5T::class,
    |            %12T(%13T::class.%11M(), "PRIVATE"),
    |            %12T(%13T::class.%11M(), "CONST"),
    |            %8T::class,
    |            %12T("com.squareup.kotlinpoet", "ARRAY"),
    |            %12T(%14T::class.%11M(), "plusParameter"),
    |            %12T("com.squareup.kotlinpoet", "asClassName"),
    |            %12T::class,
    |            %13T::class,
    |            %14T::class
    |        )
    |        .build())
    |    .build().writeTo(System.out)
    |""".trimMargin()
    FileSpec.builder(PACKAGE_NAME, FILE_NAME)
        .addProperty(PropertySpec.builder("PACKAGE_NAME", String::class, PRIVATE, CONST)
            .initializer(string, PACKAGE_NAME).build())
        .addProperty(PropertySpec.builder("FILE_NAME", String::class, PRIVATE, CONST)
            .initializer(string, FILE_NAME).build())
        .addFunction(FunSpec.builder("main").addParameter(
            "args", ARRAY.plusParameter(String::class.asClassName()))
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
                MemberName("com.squareup.kotlinpoet", "ARRAY"),
                MemberName(ParameterizedTypeName.Companion::class.asClassName(), "plusParameter"),
                MemberName("com.squareup.kotlinpoet", "asClassName"),
                MemberName::class,
                KModifier::class,
                ParameterizedTypeName.Companion::class
            )
            .build())
        .build().writeTo(System.out)
}
