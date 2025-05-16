package util.secury;

import java.util.Random;

public class CaptchaGenerator {

    private static final String[] OPERATORS = {"+", "-", "*"};
    private static final Random RANDOM = new Random();

    public static class Captcha {
        public final String question;
        public final int answer;

        public Captcha(String question, int answer) {
            this.question = question;
            this.answer = answer;
        }
    }

    public static Captcha generate() {
        int a = RANDOM.nextInt(10); // 0~9
        int b = RANDOM.nextInt(10);
        String op = OPERATORS[RANDOM.nextInt(OPERATORS.length)];

        int result;
        switch (op) {
            case "+" -> result = a + b;
            case "-" -> result = a - b;
            case "*" -> result = a * b;
            default -> throw new IllegalStateException("Unknown operator: " + op);
        }

        String question = a + " " + op + " " + b + " = ?";
        return new Captcha(question, result);
    }

    public static void main(String[] args) {
        Captcha captcha = generate();
        System.out.println("验证码问题: " + captcha.question);
        System.out.println("答案: " + captcha.answer); // 实际使用时不要返回答案！
    }
}

