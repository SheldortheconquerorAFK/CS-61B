import javax.xml.crypto.dsig.spec.HMACParameterSpec;
import java.util.HashMap;
import java.util.Map;

/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */
public class Rasterer {
    public static final double DEPTH0_LONDPP = (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) / MapServer.TILE_SIZE;

    public Rasterer() {
        // YOUR CODE HERE, AND I WOULD NOT TAKE THIS LINE OFF TILL I TOTALLY GET THROUGH PART I, AS A MARKER.


    }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     *
     *     The grid of images must obey the following properties, where image in the
     *     grid is referred to as a "tile".
     *     <ul>
     *         <li>The tiles collected must cover the most longitudinal distance per pixel
     *         (LonDPP) possible, while still covering less than or equal to the amount of
     *         longitudinal distance per pixel in the query box for the user viewport size. </li>
     *         <li>Contains all tiles that intersect the query bounding box that fulfill the
     *         above condition.</li>
     *         <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     *     </ul>
     *
     * @param params Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     *
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     *                    forget to set this to true on success! <br>
     */
    public Map<String, Object> getMapRaster(Map<String, Double> params) {
        System.out.println(params);
        Map<String, Object> results = new HashMap<>();
        results.put("render_grid", null);
        results.put("raster_ul_lon", 0);
        results.put("raster_ul_lat", 0);
        results.put("raster_lr_lon", 0);
        results.put("raster_lr_lat", 0);
        results.put("depth", 0);
        results.put("query_success", false);  // Initialize all required fields in results

        if (params.get("ullat") < MapServer.ROOT_LRLAT || params.get("ullon") > MapServer.ROOT_LRLON || params.get("lrlat") > MapServer.ROOT_ULLAT || params.get("lrlon") < MapServer.ROOT_ULLON) {
            System.out.println("Coordinates provided by request completely go off the bound of the entire map.");
            return results;
        }
        if (params.get("ullat") < params.get("lrlat") || params.get("ullon") > params.get("lrlon")) {
            System.out.println("Coordinates provided by request have wrong inequality.");
            return results;
        }

        double reqLonDPP = lonDPP(params.get("lrlon"), params.get("ullon"), params.get("w"));
        int depth = calcDepth(reqLonDPP);

        System.out.println("Since you haven't implemented getMapRaster, nothing is displayed in "
                           + "your browser.");
        return results;
    }

    private double lonDPP(double lrlon, double ullon, double width) {
        return (lrlon - ullon) / width;
    }

    private int calcDepth(double reqLonDPP) {
        double currentLonDPP = DEPTH0_LONDPP;
        int depth = 0;
        for (; depth < 8; depth++) {
            if (currentLonDPP <= reqLonDPP) {
                return depth;
            }
            currentLonDPP /= 2;
        }
        return depth;
    }
}