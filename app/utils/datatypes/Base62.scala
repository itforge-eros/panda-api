package utils.datatypes

import java.io.ByteArrayOutputStream

class Base62(alphabet: Array[Byte]) {

  def encode(message: Array[Byte]): Array[Byte] = {
    val indices = convert(message, Base62.STANDARD_BASE, Base62.TARGET_BASE)
    translate(indices, alphabet)
  }

  def decode(encoded: Array[Byte]): Array[Byte] = {
    val prepared = translate(encoded, lookupTable)
    convert(prepared, Base62.TARGET_BASE, Base62.STANDARD_BASE)
  }

  private def translate(indices: Array[Byte], dictionary: Array[Byte]): Array[Byte] = {
    indices map (dictionary(_))
  }

  private def convert(message: Array[Byte], sourceBase: Int, targetBase: Int) = {
    val estimatedLength = estimateOutputLength(message.length, sourceBase, targetBase)
    val out = new ByteArrayOutputStream(estimatedLength)
    var source = message
    while (source.length > 0) {
      val quotient = new ByteArrayOutputStream(source.length)
      var remainder = 0
      var i = 0
      while (i < source.length) {
        val accumulator = (source(i) & 0xFF) + remainder * sourceBase
        val digit = (accumulator - (accumulator % targetBase)) / targetBase
        remainder = accumulator % targetBase
        if (quotient.size > 0 || digit > 0) quotient.write(digit)
        i += 1
      }
      out.write(remainder)
      source = quotient.toByteArray
    }

    var i = 0
    while (i < message.length - 1 && message(i) == 0) {
      out.write(0)
      i += 1
    }

    out.toByteArray.reverse
  }

  private def estimateOutputLength(inputLength: Int, sourceBase: Int, targetBase: Int) = Math.ceil((Math.log(sourceBase) / Math.log(targetBase)) * inputLength).toInt

  private def createLookupTable: Array[Byte] = {
    val lookup = new Array[Byte](256)
    alphabet.indices foreach { i =>
      lookup(alphabet(i)) = (i & 0xFF).toByte
    }

    lookup
  }

  private val lookupTable = createLookupTable

}

object Base62 {

  def createInstance = createInstanceWithGmpCharacterSet

  def createInstanceWithGmpCharacterSet = new Base62(CharacterSets.GMP)

  def createInstanceWithInvertedCharacterSet = new Base62(CharacterSets.INVERTED)

  private object CharacterSets {
    val GMP = ('0' to '9') ++ ('A' to 'Z') ++ ('a' to 'z') map (_.toByte) toArray
    val INVERTED = ('0' to '9') ++ ('a' to 'z') ++ ('A' to 'Z') map (_.toByte) toArray
  }

  private val STANDARD_BASE = 256
  private val TARGET_BASE = 62

}
