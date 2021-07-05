public class Main {
    public static void main(String[] args) {
        String[] urls = new String[] {
                "https://www.aboutyou.ie/c/women/clothing/jumpers-hoodies-100212",
                "https://www.aboutyou.ie/c/women/clothing/jackets-22822",
                "https://www.aboutyou.ie/c/women/clothing/jeans-20258"
        };
        System.out.println(Parser.parse(urls));
    }
}
