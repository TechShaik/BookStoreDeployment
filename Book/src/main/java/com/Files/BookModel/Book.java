package com.Files.BookModel;





import io.micrometer.common.lang.NonNull;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@NonNull
	private long b_id;
	@NonNull
	private String b_name;
	@NonNull
	private String b_author;
	@NonNull
	private String b_description;
	@NonNull
	private float b_price;
	@NonNull
	private long b_quantity;
    @NonNull
	private String image;

}
