package com.service.local;

import java.sql.Blob;
import java.sql.SQLException;

import javax.sql.rowset.serial.SerialBlob;

import org.springframework.stereotype.Service;

@Service
public class BlobString {
	
	public Blob convertStringToBlob(String image){
		byte[] byteArray = image.getBytes();
		try {
			Blob blob = new javax.sql.rowset.serial.SerialBlob(byteArray);
			return blob;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	// Conversie inversa

	public String convertBlobToString(Blob blob) {

		// Conversie inversa
		if (blob != null) {
			byte[] bytes1 = null;
			try {
				bytes1 = blob.getBytes(1, (int) blob.length());
			} catch (SQLException e) {
				e.printStackTrace();
			}
			String s = new String(bytes1);

			return s;
		} else
			return "";
	}

}
