package com.service.local;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.awt.image.RenderedImage;
import java.awt.image.SampleModel;
import java.awt.image.WritableRaster;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.entity.DTO.PhotoAlbumDto;
import com.entity.DTO.PhotoDto;
import com.repository.model.Photo;
import com.repository.model.PhotoAlbum;

@Service
public class ImageService {

	@Autowired
	private BlobString converte;

	public void imageVerification(String image) throws Exception {
		if (!image.equals("")) {
			if (!(image.substring(11, 15).equals("jpeg") || image.substring(11, 14).equals("png")
					|| image.substring(11, 14).equals("jpg")))
				throw new Exception("Wrong format of image!");
			if (image.length() >= 150000)
				throw new Exception("Image choosed has too big size!");
		} else
			return;
	}

	public void newPhotoVerification(String image) throws Exception {
		if (!image.equals("")) {
			if (!(image.substring(11, 15).equals("jpeg") || image.substring(11, 14).equals("png")
					|| image.substring(11, 14).equals("jpg")))
				throw new Exception("Wrong format of image!");
			if (image.length() >= 4000000)
				throw new Exception("Image choosed has too big size ( max size 3MB!");
		} else
			throw new Exception("You must chose an image!");
	}

	public PhotoAlbumDto convertToDto(PhotoAlbum album) {
		PhotoAlbumDto dtoAlbum = new PhotoAlbumDto();
		dtoAlbum.setId(album.getId());
		dtoAlbum.setName(album.getName());
		dtoAlbum.setDescription(album.getDescription());
		dtoAlbum.setDate(album.getDate());
		dtoAlbum.setCategory(album.getCategory());
		dtoAlbum.setCoverImage(this.converte.convertBlobToString(album.getCoverImage()));
		return dtoAlbum;
	}
	
	public List<PhotoDto> convertPhotoToDto(List<Photo> photoList){
		List<PhotoDto> dtoList= new ArrayList<PhotoDto>();
		String image;
		for(Photo photo : photoList){
			if(this.converte.convertBlobToString(photo.getImage()).length()>50000)
				image=this.resizeImageWidthAndHeight(photo.getImage());
			else
				image=this.converter.convertBlobToString(photo.getImage());
			dtoList.add(new PhotoDto(
					photo.getId(),
					photo.getName(),
					photo.getDescription(),
					photo.getCategory(),
					photo.getVisualisations(),
					photo.getRating(),
					photo.getDate(),
					image
			));
		}
		return dtoList;
	}

	// ###########################################################################################

	/*
	 * Extract type
	 */
	public String getImageFormat(String image) {
		if (image.substring(11, 14).equals("jpg"))
			return image.substring(11, 14);
		if (image.substring(11, 15).equals("jpeg"))
			return image.substring(11, 15);
		if (image.substring(11, 14).equals("png"))
			return image.substring(11, 14);
		return null;
	}

	public void saveImage(byte[] bytes) {
		OutputStream out = null;

		try {
		    out = new BufferedOutputStream(new FileOutputStream("image"));
		    out.write(bytes);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
		    if (out != null)
				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
	
	/*#########################################
	NOTE:
		Resize image 
		return Buffer
	##########################################*/
	@Autowired
	private BlobString converter;
	
	private BufferedImage convertBlobToBufferedImage(Blob blobImage){
		String imageCode=this.converter.convertBlobToString(blobImage);
		
		//making one split from first , (from there is starting the image
		String base64Image = imageCode.split(",")[1];
		// Convert the image code to bytes.
		byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64Image);
		BufferedImage bufferedImage = null;
		try {
			bufferedImage = ImageIO.read(new ByteArrayInputStream(imageBytes));
			return bufferedImage;
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return null;
	}
	
	private static String imgToBase64String(final RenderedImage img, final String formatName) {
	    final ByteArrayOutputStream os = new ByteArrayOutputStream();
	    try {
	        ImageIO.write(img, formatName, Base64.getEncoder().wrap(os));
	        return os.toString(StandardCharsets.ISO_8859_1.name());
	    } catch (final IOException ioe) {
	        throw new UncheckedIOException(ioe);
	    }
	}
	
	
	private static final int IMG_WIDTH=625;
	private static final int IMG_HEIGHT=576;
	
	private BufferedImage resizeImageWithHint(BufferedImage originalImage, int type) {

		BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
		g.dispose();
		g.setComposite(AlphaComposite.Src);

		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		return resizedImage;
	}
	
	public String resizeImageWidthAndHeight(Blob blobImage){
		
		return "data:image/jpeg;base64,"+imgToBase64String(this.resizeImageWithHint
				(this.convertBlobToBufferedImage(blobImage), BufferedImage.TYPE_INT_ARGB) , "png");
	}
	
	
}
