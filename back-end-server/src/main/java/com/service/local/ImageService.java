package com.service.local;

import java.awt.Image;
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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.entity.DTO.PhotoAlbumDto;
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

	// ###########################################################################################

	/*
	 * Extract type
	 */
	private String getImageFormat(String image) {
		if (image.substring(11, 14).equals("jpg"))
			return image.substring(11, 14);
		if (image.substring(11, 15).equals("jpeg"))
			return image.substring(11, 15);
		if (image.substring(11, 14).equals("png"))
			return image.substring(11, 14);
		return null;
	}

	// /* Tools methods */
	//
	// private static BufferedImage convertByteArrayToBufferedImage(byte[]
	// byteArray,String type) {
	//
	//// DataBufferByte buffer = new DataBufferByte(byteArray,
	// byteArray.length);
	////
	//// ColorModel cm = new
	// ComponentColorModel(ColorSpace.getInstance(ColorSpace.CS_sRGB), new int[]
	// { 8, 8, 8 },
	//// false, false, Transparency.OPAQUE, DataBuffer.TYPE_BYTE);
	////
	//// BufferedImage image= new BufferedImage(cm,
	//// Raster.createInterleavedRaster(buffer, 200, 200, 200 * 3, 3,
	//// new int[] { 0, 1, 2 }, null), false, null);
	//
	// final ImageReader reader =
	// ImageIO.getImageReadersByFormatName(type).next();
	// final InputStream is = new ByteArrayInputStream(byteArray);
	// try {
	// final ImageInputStream imageInput = ImageIO.createImageInputStream(is);
	// reader.setInput(imageInput);
	// final BufferedImage image = reader.read(0);
	// is.close();
	// return image;
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// return null;
	// }
	//
	// private byte[] convertBufferedImageToByteArray(BufferedImage image,
	// String type) {
	// ByteArrayOutputStream baos = new ByteArrayOutputStream();
	// try {
	// ImageIO.write(image, type, baos);
	// byte[] bytes = baos.toByteArray();
	// return bytes;
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// return null;
	// }
	//
	// /*
	// * Resize image as buffered image
	// */
	// public byte[] resizeImage(String imageString, byte[] imageByteArray) {
	// BufferedImage originalImage =
	// ImageService.convertByteArrayToBufferedImage(imageByteArray,this.getImageFormat(imageString));
	// return this.convertBufferedImageToByteArray(originalImage,
	// this.getImageFormat(imageString));
	// }

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
}
