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

    public Rasterer() { }

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

        if (params.get("ullat") <= MapServer.ROOT_LRLAT || params.get("ullon") >= MapServer.ROOT_LRLON || params.get("lrlat") >= MapServer.ROOT_ULLAT || params.get("lrlon") <= MapServer.ROOT_ULLON) {
            System.out.println("Coordinates provided by request completely go off the bound of the entire map.");
            return results;
        }
        if (params.get("ullat") <= params.get("lrlat") || params.get("ullon") >= params.get("lrlon")) {
            System.out.println("Coordinates provided by request have wrong inequality.");
            return results;
        }

        double reqLonDPP = lonDPP(params.get("ullon"), params.get("lrlon"), params.get("w"));
        int depth = calcDepth(reqLonDPP);
        int[] xIndex = calcXIndex(params.get("ullon"), params.get("lrlon"), depth);
        int startX = xIndex[0];
        int endX = xIndex[1];
        int[] yIndex = calcYIndex(params.get("ullat"), params.get("lrlat"), depth);
        int startY = yIndex[0];
        int endY = yIndex[1];
        double tileWidth = (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) / (Math.pow(2, depth));
        double tileHeight = (MapServer.ROOT_ULLAT - MapServer.ROOT_LRLAT) / (Math.pow(2, depth));

        String[][] renderGrid = new String[endY - startY + 1][endX - startX + 1];
        for (int y = 0; y < endY - startY + 1; y++) {
            for (int x = 0; x < endX - startX + 1; x++) {
                renderGrid[y][x] = String.format("d%1$d_x%2$d_y%3$d.png", depth, x + startX, y + startY);
            }
        }

        double rasterullon = MapServer.ROOT_ULLON + startX * tileWidth;
        double rasterullat = MapServer.ROOT_ULLAT - startY * tileHeight;
        double rasterlrlon = MapServer.ROOT_ULLON + (endX + 1) * tileWidth;
        double rasterlrlat = MapServer.ROOT_ULLAT - (endY + 1) * tileHeight;

        results.put("render_grid", renderGrid);
        results.put("raster_ul_lon", rasterullon);
        results.put("raster_ul_lat", rasterullat);
        results.put("raster_lr_lon", rasterlrlon);
        results.put("raster_lr_lat", rasterlrlat);
        results.put("depth", depth);
        results.put("query_success", true);
        return results;
    }

    private double lonDPP(double ullon, double lrlon, double width) {
        return (lrlon - ullon) / width;
    }

    private int calcDepth(double reqLonDPP) {
        double currentLonDPP = DEPTH0_LONDPP;
        int depth = 0;
        for (; depth < 7; depth++) {
            if (currentLonDPP <= reqLonDPP) {
                return depth;
            }
            currentLonDPP /= 2;
        }
        return depth;
    }

    private int[] calcXIndex(double ullon, double lrlon, int depth) {
        double tileWidth = (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) / (Math.pow(2, depth));
        int[] xIndex = new int[2];
        int startXIndex = (int) ((ullon - MapServer.ROOT_ULLON) / tileWidth);
        if (startXIndex < 0) {
            startXIndex = 0; // In this case the left bond of query box goes off that of the entire map, so cut that part off and make x0 as start index
        }
        xIndex[0] = startXIndex;

        int endXIndex = (int) ((lrlon - MapServer.ROOT_ULLON) / tileWidth);
        if (lrlon >= MapServer.ROOT_LRLON) {
            endXIndex = (int) Math.pow(2, depth) - 1;
        }
        xIndex[1] = endXIndex;
        return xIndex;
    }

    private int[] calcYIndex(double ullat, double lrlat, int depth) {
        double tileHeight = (MapServer.ROOT_ULLAT - MapServer.ROOT_LRLAT) / (Math.pow(2, depth));
        int[] yIndex = new int[2];
        int startYIndex = (int) ((MapServer.ROOT_ULLAT - ullat) / tileHeight);
        if (startYIndex < 0) {
            startYIndex = 0; // In this case the upper bond of query box goes off that of the entire map, so cut that part off and make y0 as start index
        }
        yIndex[0] = startYIndex;

        int endYIndex = (int) ((MapServer.ROOT_ULLAT - lrlat) / tileHeight);
        if (lrlat <= MapServer.ROOT_LRLAT) {
            endYIndex = (int) Math.pow(2, depth) - 1;
        }
        yIndex[1] = endYIndex;
        return yIndex;
    }
}