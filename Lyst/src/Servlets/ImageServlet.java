package Servlets;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.file.Files;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.coobird.thumbnailator.Thumbnails;

/**
 * The Image servlet for serving from absolute path.
 * @author BalusC
 * @link http://balusc.blogspot.com/2007/04/imageservlet.html
 */
@WebServlet("/image/*")
public class ImageServlet extends HttpServlet {

    // Properties ---------------------------------------------------------------------------------

    private String imagePath;

    // Init ---------------------------------------------------------------------------------------

    public void init() throws ServletException {

        // Define base path somehow. You can define it as init-param of the servlet.
        this.imagePath = "https://s3-us-west-2.amazonaws.com/elasticbeanstalk-us-west-2-119295481920/Images";

        // In a Windows environment with the Applicationserver running on the
        // c: volume, the above path is exactly the same as "c:\var\webapp\images".
        // In Linux/Mac/UNIX, it is just straightforward "/var/webapp/images".
    }

    // Actions ------------------------------------------------------------------------------------

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	System.out.println("inside image servlet");
        // Get requested image by path info.
        String requestedImage = request.getPathInfo();

        // Check if file name is actually supplied to the request URI.
        if (requestedImage == null) {
            // Do your thing if the image is not supplied to the request URI.
            // Throw an exception, or send 404, or show default/warning image, or just ignore it.
            response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
            return;
        }

        // Decode the file name (might contain spaces and on) and prepare file object.
        URL url = new URL(imagePath+requestedImage);   
//        BufferedImage a = ImageIO.read(url);
//        Dimension imgSize = new Dimension(a.getWidth(), a.getHeight());
//        Dimension boundary = new Dimension(100, 100);
//        Dimension newSize = getScaledDimension(imgSize,boundary);
//        
//
//        // Init servlet response.
//        response.reset();
//        
//        Thumbnails.of(url)
//        .size(newSize.width, newSize.height)
//        .outputFormat("png")
//        .toFile("tmp.png");
//        
//        File tmp = new File("tmp.png");
//        // Get content type by filename.
//        String contentType = getServletContext().getMimeType(tmp.getName());
//
//        // Check if file is actually an image (avoid download of other files by hackers!).
//        // For all content types, see: http://www.w3schools.com/media/media_mimeref.asp
//        if (contentType == null || !contentType.startsWith("image")) {
//            // Do your thing if the file appears not being a real image.
//            // Throw an exception, or send 404, or show default/warning image, or just ignore it.
//            response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
//            return;
//        }
//        response.setContentType(contentType);
//        response.setHeader("Content-Length", String.valueOf(tmp.length()));
        
        Thumbnails.of(url)
        .scale(.2)
        .outputFormat("png")
        .toOutputStream(response.getOutputStream());
        
        System.out.println("Bruh "+ response);

        //ImageIO.write(a, "jpg", response.getOutputStream());
        // Write image content to response.
        //Files.copy(image.toPath(), response.getOutputStream());
        //response.getOutputStream().close();
        //response.setHeader("Content-Length", response.getOutputStream().siz);
//        ByteArrayOutputStream tmp = new ByteArrayOutputStream();
//        ImageIO.write(b, "png", tmp);
//        tmp.close();
//        Integer contentLength = tmp.size();

//        response.setHeader("Content-Length",contentLength.toString());
//        OutputStream out = response.getOutputStream();
//        out.write(tmp.toByteArray());
//        out.close();
    }
    
    public BufferedImage scale(BufferedImage img, int targetWidth, int targetHeight) {

        int type = (img.getTransparency() == Transparency.OPAQUE) ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
        BufferedImage ret = img;
        BufferedImage scratchImage = null;
        Graphics2D g2 = null;

        int w = img.getWidth();
        int h = img.getHeight();

        int prevW = w;
        int prevH = h;

        do {
            if (w > targetWidth) {
                w /= 2;
                w = (w < targetWidth) ? targetWidth : w;
            }

            if (h > targetHeight) {
                h /= 2;
                h = (h < targetHeight) ? targetHeight : h;
            }

            if (scratchImage == null) {
                scratchImage = new BufferedImage(w, h, type);
                g2 = scratchImage.createGraphics();
            }

            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2.drawImage(ret, 0, 0, w, h, 0, 0, prevW, prevH, null);

            prevW = w;
            prevH = h;
            ret = scratchImage;
        } while (w != targetWidth || h != targetHeight);

        if (g2 != null) {
            g2.dispose();
        }

        if (targetWidth != ret.getWidth() || targetHeight != ret.getHeight()) {
            scratchImage = new BufferedImage(targetWidth, targetHeight, type);
            g2 = scratchImage.createGraphics();
            g2.drawImage(ret, 0, 0, null);
            g2.dispose();
            ret = scratchImage;
        }

        return ret;

    }
    
    public static Dimension getScaledDimension(Dimension imgSize, Dimension boundary) {

        int original_width = imgSize.width;
        int original_height = imgSize.height;
        int bound_width = boundary.width;
        int bound_height = boundary.height;
        int new_width = original_width;
        int new_height = original_height;

        // first check if we need to scale width
        if (original_width > bound_width) {
            //scale width to fit
            new_width = bound_width;
            //scale height to maintain aspect ratio
            new_height = (new_width * original_height) / original_width;
        }

        // then check if we need to scale even with the new height
        if (new_height > bound_height) {
            //scale height to fit instead
            new_height = bound_height;
            //scale width to maintain aspect ratio
            new_width = (new_height * original_width) / original_height;
        }

        return new Dimension(new_width, new_height);
    }
    
}