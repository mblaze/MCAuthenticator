package io.ibj.mcauthenticator.map;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

import java.awt.Image;

/**
 * @author Joseph Hirschfeld
 * @date 1/11/2016
 */
public final class ImageMapRenderer extends MapRenderer {

    private static final String TOTP_URL_FORMAT = "otpauth://totp/%s@%s?secret=%s";

    public ImageMapRenderer(String username, String secret, String serverip) throws WriterException {
        this.qrCode = createQRCode(username, secret, serverip);
    }

    private final Image qrCode;

    @Override
    public void render(MapView mapView, MapCanvas mapCanvas, Player player) {
        mapCanvas.drawImage(0, 0, qrCode);
    }

    private Image createQRCode(String username, String secret, String serverIp) throws WriterException {
        String contents = String.format(
                TOTP_URL_FORMAT,
                username,
                serverIp,
                secret
        );
        BitMatrix bits = new QRCodeWriter().encode(
                contents,
                BarcodeFormat.QR_CODE,
                128,
                128
        );
        return MatrixToImageWriter.toBufferedImage(bits);
    }
}
