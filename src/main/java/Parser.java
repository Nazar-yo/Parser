import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Parser {
    @SuppressWarnings("unchecked")
    public static String parse(String[] urls) {
        ColorUtils colorUtils = new ColorUtils();
        JSONArray jsonFile = new JSONArray();
        Document document;
        for (String url : urls) {
            try {
                document = Jsoup.connect(url).get();
            } catch (IOException e) {
                throw new RuntimeException("Can`t connect", e);
            }
            Elements clothes = document.select("a.sc-1qheze-0.dgBQdu");
            for (Element element : clothes) {
                JSONObject jsonProduct = new JSONObject();
                jsonProduct.put("Id:", element.id());
                jsonProduct.put("ProductTitle:", element.select("img").first().attr("alt"));
                jsonProduct.put("Brand:", element.select("p.sc-1gv4rhx-2.cYTPsp").first().text());
                JSONArray colors = new JSONArray();
                Elements colorsValues = element.select("li");
                for (Element color : colorsValues) {
                    if (color.toString().contains("color")) {
                        colors.add(colorUtils.getColorNameFromHex(
                                Integer.decode(color.attr("color").toLowerCase())));
                    }
                }
                jsonProduct.put("Colors:", colors);
                jsonProduct.put("Sizes:", element.select("span").last().text());
                jsonProduct.put("Price:", element.select("span").first().text());
                jsonFile.add(jsonProduct);
            }
        }
        try (FileWriter writer = new FileWriter("products.json")) {
            writer.write(jsonFile.toJSONString());
        } catch (IOException e) {
            throw new RuntimeException("Can`t write to file products.json");
        }
        return "Amount of URLs : " + urls.length + ", amount of extracted Products : " + jsonFile.size();
    }
}
