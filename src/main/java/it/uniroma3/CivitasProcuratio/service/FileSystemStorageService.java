package it.uniroma3.CivitasProcuratio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.CivitasProcuratio.exception.StorageException;
import it.uniroma3.CivitasProcuratio.exception.StorageFileNotFoundException;
import it.uniroma3.CivitasProcuratio.property.StorageProperties;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Service
public class FileSystemStorageService implements StorageService {

	private final Path rootLocation;

	@Autowired
	public FileSystemStorageService(StorageProperties properties) {
		this.rootLocation = Paths.get(properties.getLocation());
	}

	@Override
	public void store(MultipartFile file,Path destinationFolder) {
		try {
			if (file.isEmpty()) {
				throw new StorageException("Failed to store empty file " + file.getOriginalFilename());
			}
			if (Files.exists(destinationFolder.resolve(file.getOriginalFilename()))) {
				throw new StorageException("Failed to store because already exists " + file.getOriginalFilename());
			}
			Files.copy(file.getInputStream(), destinationFolder.resolve(file.getOriginalFilename()));
		} catch (IOException e) {
			throw new StorageException("Failed to store file " + file.getOriginalFilename(), e);
		}
	}

	@Override
	public Stream<Path> loadAll(Path destinationFolder) {
		try {
			if (!Files.exists(destinationFolder)) {
				Files.createDirectories(destinationFolder);
			}
			return Files.walk(destinationFolder, 2).filter(path -> !path.equals(destinationFolder))
					.map(path -> this.rootLocation.relativize(path));

		} catch (IOException e) {
			throw new StorageException("Failed to read stored files", e);
		}

	}


	@Override
	public Resource loadAsResource(String filename, Path folder) {
		try {
			Path file = folder.resolve(filename);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new StorageFileNotFoundException("Could not read file: " + filename);

			}
		} catch (MalformedURLException e) {
			throw new StorageFileNotFoundException("Could not read file: " + filename, e);
		}
	}

	@Override
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(rootLocation.toFile());
	}

	@Override
	public void init() {
		try {
			Files.createDirectory(rootLocation);
		} catch (IOException e) {
			throw new StorageException("Could not initialize storage", e);
		}
	}

	@Override
	public Path getRootLocation() {
		return this.rootLocation;
	}

}
