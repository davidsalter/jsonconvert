package com.davidsalter.jsonconvert

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.Context
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.file
import com.google.gson.*
import java.io.FileReader
import java.io.FileWriter
import java.util.*

class JsonConvert : CliktCommand(name="jsonconvert") {
    override fun help(context: Context): String {
        return "Convert attribute names in a Json file to either lower or upper case. If a conversion to upper or lower case is not specified, the input file will default to having attributes converted to lower case.\n\n(c) David Salter 2024"
    }

    private val lower by option(
        "-l",
        "--lower",
        help = "Convert attribute names to lower case. (The default option)"
    ).flag(
        default = true
    )
    private val upper by option("-u", "--upper", help = "Convert attribute names to upper case.").flag()
    private val input by argument("input", "Read Json to convert from this file").file(mustExist = true)
    private val output by argument("output", "Write converted Json to this file").file(canBeFile = true)

    override fun run() {
        val reader = FileReader(input)
        val jsonElement = JsonParser.parseReader(reader)
        reader.close()

        var transformedJson: JsonElement? = null
        if (lower) transformedJson = convertAttributeNamesCase(jsonElement, Transformation.TO_LOWER)
        else if (upper) transformedJson = convertAttributeNamesCase(jsonElement, Transformation.TO_UPPER)

        val writer = FileWriter(output)
        GsonBuilder().setPrettyPrinting().create().toJson(transformedJson, writer)
        writer.close()
    }
}

fun convertAttributeNamesCase(element: JsonElement, transformation: Transformation): JsonElement {
    return when (element) {
        is JsonObject -> {
            val transformedObject = JsonObject()
            for ((key, value) in element.entrySet()) {
                when (transformation) {
                    Transformation.TO_UPPER -> transformedObject.add(
                        key.uppercase(Locale.getDefault()), convertAttributeNamesCase(value, transformation)
                    )

                    Transformation.TO_LOWER -> transformedObject.add(
                        key.lowercase(Locale.getDefault()), convertAttributeNamesCase(value, transformation)
                    )
                }
            }
            transformedObject
        }

        is JsonArray -> {
            val transformedArray = JsonArray()
            element.forEach { item -> transformedArray.add(convertAttributeNamesCase(item, transformation)) }
            transformedArray
        }

        else -> element
    }
}

enum class Transformation {
    TO_UPPER, TO_LOWER
}