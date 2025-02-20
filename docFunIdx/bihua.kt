
import java.awt.font.TextAttribute
import java.awt.Font

fun getStrokeCountWin(ch: Char): Int {
    val font = Font("SimSun", Font.PLAIN, 12) // 使用宋体
    val attributes = mapOf(TextAttribute.LIGATURES to ch)
    val glyphVector = font.createGlyphVector(null, charArrayOf(ch))
    return glyphVector.numGlyphs
}

fun main() {
    println(getStrokeCountWin('汉')) // 5
    println(getStrokeCountWin('字')) // 6
}
