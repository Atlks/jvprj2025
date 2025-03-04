//package exmp;
//
//import java.awt.Font;
//import java.awt.Graphics2D;
//import java.awt.font.GlyphVector;
//import java.awt.geom.Path2D;
//import java.awt.Shape;
//
//public class GlyphComplexity {
//
//    public static void main(String[] args) {
//        try {
//            String character = "属";
//            Font font = new Font("宋体", Font.PLAIN, 72); // 使用合适的字体和大小
//            GlyphVector glyphVector = font.createGlyphVector(Graphics2D.getGraphicsConfiguration().getDefaultTransform(), character);
//
//            // 计算复杂度
//            int complexity = calculateComplexity(glyphVector);
//            System.out.println("Character: " + character + " Complexity: " + complexity);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    // 计算字形复杂度：通过路径点数来估算
//    public static int calculateComplexity(GlyphVector glyphVector) {
//        int complexity = 0;
//
//        for (int i = 0; i < glyphVector.getNumGlyphs(); i++) {
//            Shape glyphOutline = glyphVector.getGlyphOutline(i);  // 获取字形的轮廓
//            if (glyphOutline instanceof Path2D) {
//                Path2D path = (Path2D) glyphOutline;
//                complexity += path.getCurrentPoint() != null ? 1 : 0;  // 简单估算路径点数
//            }
//        }
//
//        return complexity;
//    }
//}
//
