package it.uniroma3.CivitasProcuratio.service;

import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface StorageService {
	void init();

    Stream<Path> loadAll(Path folder);

    Resource loadAsResource(String filename, Path folder);

    void deleteAll();

	void store(MultipartFile file, Path destinationFolder);

	Path getRootLocation();

}
