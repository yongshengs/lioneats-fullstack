package com.lioneats.lioneats_backend.util;

public class UserShopGeoUtil {

    private static final int EARTH_RADIUS_KM = 6371;

    /**
     * Calculates the distance between two points specified by latitude/longitude using the Haversine formula.
     *
     * @param userLat The latitude of the user.
     * @param userLng The longitude of the user.
     * @param shopLat The latitude of the shop.
     * @param shopLng The longitude of the shop.
     * @return The distance between the two points in kilometers.
     */
    public static double calculateDistance(double userLat, double userLng, double shopLat, double shopLng) {
        double latDistance = Math.toRadians(shopLat - userLat);
        double lngDistance = Math.toRadians(shopLng - userLng);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(userLat)) * Math.cos(Math.toRadians(shopLat))
                * Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS_KM * c * 1000;
    }
}

