package com.gruposura.citadels;

import com.gruposura.citadels.model.ConstructionConfig;
import com.gruposura.citadels.model.ConstructionConfigDetail;
import com.gruposura.citadels.model.Inventory;
import com.gruposura.citadels.model.Product;
import com.gruposura.citadels.repository.ConstructionConfigDetailRepository;
import com.gruposura.citadels.repository.ConstructionConfigRepository;
import com.gruposura.citadels.repository.InventoryRepository;
import com.gruposura.citadels.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableJpaRepositories("com.gruposura.citadels.repository")
@EntityScan("com.gruposura.citadels.model")
@ComponentScan("com.gruposura.citadels.*")
@EnableAsync
public class Application implements CommandLineRunner {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ConstructionConfigRepository constructionConfigRepository;

    @Autowired
    private ConstructionConfigDetailRepository constructionConfigDetailRepository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Product productCemento = new Product();
        productCemento.setName("CEMENTO");
        productCemento.setReference("MTRL-1");

        Product productGrava = new Product();
        productGrava.setName("GRAVA");
        productGrava.setReference("MTRL-2");

        Product productArena = new Product();
        productArena.setName("ARENA");
        productArena.setReference("MTRL-3");

        Product productMadera = new Product();
        productMadera.setName("MADERA");
        productMadera.setReference("MTRL-4");

        Product productAdobe = new Product();
        productAdobe.setName("ADOBE");
        productAdobe.setReference("MTRL-5");

        List<Product> productList = new ArrayList<>();
        productList.add(productCemento);
        productList.add(productGrava);
        productList.add(productArena);
        productList.add(productMadera);
        productList.add(productAdobe);

        List<Product> products = productRepository.saveAll(productList);

        for (Product product : products) {
            Inventory inventory = new Inventory();
            inventory.setProduct(product);
            inventory.setQuantity(1000);
            inventory.setUnitCost(50000d);
            inventoryRepository.save(inventory);
        }


        List<ConstructionConfig> configs = new ArrayList<>();

        ConstructionConfig configCasa = new ConstructionConfig();
        configCasa.setName("Casa");
        configCasa.setTotalDays(3);

        ConstructionConfig configLago = new ConstructionConfig();
        configLago.setName("Lago");
        configLago.setTotalDays(2);

        ConstructionConfig configCancha = new ConstructionConfig();
        configCancha.setName("Cancha de fútbol");
        configCancha.setTotalDays(1);

        ConstructionConfig configEdificio = new ConstructionConfig();
        configEdificio.setName("Edificio");
        configEdificio.setTotalDays(6);

        ConstructionConfig configGimnasio = new ConstructionConfig();
        configGimnasio.setName("Gimnasio");
        configGimnasio.setTotalDays(2);

        configs.add(configCasa);
        configs.add(configLago);
        configs.add(configCancha);
        configs.add(configEdificio);
        configs.add(configGimnasio);

        constructionConfigRepository.saveAll(configs);

        List<ConstructionConfigDetail> detailsCasa = new ArrayList<>();
        ConstructionConfigDetail detailCasaCemento = new ConstructionConfigDetail();
        detailCasaCemento.setProductId(productRepository.findByName(productCemento.getName()).get().getId());
        detailCasaCemento.setQuantity(100);
        detailCasaCemento.setConfig(constructionConfigRepository.findByName(configCasa.getName()).get());

        ConstructionConfigDetail detailCasaGrava = new ConstructionConfigDetail();
        detailCasaGrava.setProductId(productRepository.findByName(productGrava.getName()).get().getId());
        detailCasaGrava.setQuantity(50);
        detailCasaGrava.setConfig(constructionConfigRepository.findByName(configCasa.getName()).get());

        ConstructionConfigDetail detailCasaArena = new ConstructionConfigDetail();
        detailCasaArena.setProductId(productRepository.findByName(productArena.getName()).get().getId());
        detailCasaArena.setQuantity(90);
        detailCasaArena.setConfig(constructionConfigRepository.findByName(configCasa.getName()).get());

        ConstructionConfigDetail detailCasaMadera = new ConstructionConfigDetail();
        detailCasaMadera.setProductId(productRepository.findByName(productMadera.getName()).get().getId());
        detailCasaMadera.setQuantity(20);
        detailCasaMadera.setConfig(constructionConfigRepository.findByName(configCasa.getName()).get());

        ConstructionConfigDetail detailCasaAdobe = new ConstructionConfigDetail();
        detailCasaAdobe.setProductId(productRepository.findByName(productAdobe.getName()).get().getId());
        detailCasaAdobe.setQuantity(100);
        detailCasaAdobe.setConfig(constructionConfigRepository.findByName(configCasa.getName()).get());

        detailsCasa.add(detailCasaCemento);
        detailsCasa.add(detailCasaGrava);
        detailsCasa.add(detailCasaArena);
        detailsCasa.add(detailCasaMadera);
        detailsCasa.add(detailCasaAdobe);

        constructionConfigDetailRepository.saveAll(detailsCasa);


        List<ConstructionConfigDetail> detailsLago = new ArrayList<>();
        ConstructionConfigDetail detailLagoCemento = new ConstructionConfigDetail();
        detailLagoCemento.setProductId(productRepository.findByName(productCemento.getName()).get().getId());
        detailLagoCemento.setQuantity(50);
        detailLagoCemento.setConfig(constructionConfigRepository.findByName(configLago.getName()).get());

        ConstructionConfigDetail detailLagoGrava = new ConstructionConfigDetail();
        detailLagoGrava.setProductId(productRepository.findByName(productGrava.getName()).get().getId());
        detailLagoGrava.setQuantity(60);
        detailLagoGrava.setConfig(constructionConfigRepository.findByName(configLago.getName()).get());

        ConstructionConfigDetail detailLagoArena = new ConstructionConfigDetail();
        detailLagoArena.setProductId(productRepository.findByName(productArena.getName()).get().getId());
        detailLagoArena.setQuantity(80);
        detailLagoArena.setConfig(constructionConfigRepository.findByName(configLago.getName()).get());

        ConstructionConfigDetail detailLagoMadera = new ConstructionConfigDetail();
        detailLagoMadera.setProductId(productRepository.findByName(productMadera.getName()).get().getId());
        detailLagoMadera.setQuantity(10);
        detailLagoMadera.setConfig(constructionConfigRepository.findByName(configLago.getName()).get());

        ConstructionConfigDetail detailLagoAdobe = new ConstructionConfigDetail();
        detailLagoAdobe.setProductId(productRepository.findByName(productAdobe.getName()).get().getId());
        detailLagoAdobe.setQuantity(20);
        detailLagoAdobe.setConfig(constructionConfigRepository.findByName(configLago.getName()).get());

        detailsLago.add(detailLagoCemento);
        detailsLago.add(detailLagoGrava);
        detailsLago.add(detailLagoArena);
        detailsLago.add(detailLagoMadera);
        detailsLago.add(detailLagoAdobe);

        constructionConfigDetailRepository.saveAll(detailsLago);


        List<ConstructionConfigDetail> detailsCancha = new ArrayList<>();
        ConstructionConfigDetail detailCanchaCemento = new ConstructionConfigDetail();
        detailCanchaCemento.setProductId(productRepository.findByName(productCemento.getName()).get().getId());
        detailCanchaCemento.setQuantity(20);
        detailCanchaCemento.setConfig(constructionConfigRepository.findByName(configCancha.getName()).get());

        ConstructionConfigDetail detailCanchaGrava = new ConstructionConfigDetail();
        detailCanchaGrava.setProductId(productRepository.findByName(productGrava.getName()).get().getId());
        detailCanchaGrava.setQuantity(20);
        detailCanchaGrava.setConfig(constructionConfigRepository.findByName(configCancha.getName()).get());

        ConstructionConfigDetail detailCanchaArena = new ConstructionConfigDetail();
        detailCanchaArena.setProductId(productRepository.findByName(productArena.getName()).get().getId());
        detailCanchaArena.setQuantity(20);
        detailCanchaArena.setConfig(constructionConfigRepository.findByName(configCancha.getName()).get());

        ConstructionConfigDetail detailCanchaMadera = new ConstructionConfigDetail();
        detailCanchaMadera.setProductId(productRepository.findByName(productMadera.getName()).get().getId());
        detailCanchaMadera.setQuantity(20);
        detailCanchaMadera.setConfig(constructionConfigRepository.findByName(configCancha.getName()).get());

        ConstructionConfigDetail detailCanchaAdobe = new ConstructionConfigDetail();
        detailCanchaAdobe.setProductId(productRepository.findByName(productAdobe.getName()).get().getId());
        detailCanchaAdobe.setQuantity(20);
        detailCanchaAdobe.setConfig(constructionConfigRepository.findByName(configCancha.getName()).get());

        detailsCancha.add(detailCanchaCemento);
        detailsCancha.add(detailCanchaGrava);
        detailsCancha.add(detailCanchaArena);
        detailsCancha.add(detailCanchaMadera);
        detailsCancha.add(detailCanchaAdobe);

        constructionConfigDetailRepository.saveAll(detailsCancha);


        List<ConstructionConfigDetail> detailsEdificio = new ArrayList<>();
        ConstructionConfigDetail detailEdificioCemento = new ConstructionConfigDetail();
        detailEdificioCemento.setProductId(productRepository.findByName(productCemento.getName()).get().getId());
        detailEdificioCemento.setQuantity(20);
        detailEdificioCemento.setConfig(constructionConfigRepository.findByName(configEdificio.getName()).get());

        ConstructionConfigDetail detailEdificioGrava = new ConstructionConfigDetail();
        detailEdificioGrava.setProductId(productRepository.findByName(productGrava.getName()).get().getId());
        detailEdificioGrava.setQuantity(20);
        detailEdificioGrava.setConfig(constructionConfigRepository.findByName(configEdificio.getName()).get());

        ConstructionConfigDetail detailEdificioArena = new ConstructionConfigDetail();
        detailEdificioArena.setProductId(productRepository.findByName(productArena.getName()).get().getId());
        detailEdificioArena.setQuantity(20);
        detailEdificioArena.setConfig(constructionConfigRepository.findByName(configEdificio.getName()).get());

        ConstructionConfigDetail detailEdificioMadera = new ConstructionConfigDetail();
        detailEdificioMadera.setProductId(productRepository.findByName(productMadera.getName()).get().getId());
        detailEdificioMadera.setQuantity(20);
        detailEdificioMadera.setConfig(constructionConfigRepository.findByName(configEdificio.getName()).get());

        ConstructionConfigDetail detailEdificioAdobe = new ConstructionConfigDetail();
        detailEdificioAdobe.setProductId(productRepository.findByName(productAdobe.getName()).get().getId());
        detailEdificioAdobe.setQuantity(20);
        detailEdificioAdobe.setConfig(constructionConfigRepository.findByName(configEdificio.getName()).get());

        detailsEdificio.add(detailEdificioCemento);
        detailsEdificio.add(detailEdificioGrava);
        detailsEdificio.add(detailEdificioArena);
        detailsEdificio.add(detailEdificioMadera);
        detailsEdificio.add(detailEdificioAdobe);

        constructionConfigDetailRepository.saveAll(detailsEdificio);


        List<ConstructionConfigDetail> detailsGimnasio = new ArrayList<>();
        ConstructionConfigDetail detailGimnasioCemento = new ConstructionConfigDetail();
        detailGimnasioCemento.setProductId(productRepository.findByName(productCemento.getName()).get().getId());
        detailGimnasioCemento.setQuantity(50);
        detailGimnasioCemento.setConfig(constructionConfigRepository.findByName(configGimnasio.getName()).get());

        ConstructionConfigDetail detailGimnasioGrava = new ConstructionConfigDetail();
        detailGimnasioGrava.setProductId(productRepository.findByName(productGrava.getName()).get().getId());
        detailGimnasioGrava.setQuantity(25);
        detailGimnasioGrava.setConfig(constructionConfigRepository.findByName(configGimnasio.getName()).get());

        ConstructionConfigDetail detailGimnasioArena = new ConstructionConfigDetail();
        detailGimnasioArena.setProductId(productRepository.findByName(productArena.getName()).get().getId());
        detailGimnasioArena.setQuantity(45);
        detailGimnasioArena.setConfig(constructionConfigRepository.findByName(configGimnasio.getName()).get());

        ConstructionConfigDetail detailGimnasioMadera = new ConstructionConfigDetail();
        detailGimnasioMadera.setProductId(productRepository.findByName(productMadera.getName()).get().getId());
        detailGimnasioMadera.setQuantity(10);
        detailGimnasioMadera.setConfig(constructionConfigRepository.findByName(configGimnasio.getName()).get());

        ConstructionConfigDetail detailGimnasioAdobe = new ConstructionConfigDetail();
        detailGimnasioAdobe.setProductId(productRepository.findByName(productAdobe.getName()).get().getId());
        detailGimnasioAdobe.setQuantity(50);
        detailGimnasioAdobe.setConfig(constructionConfigRepository.findByName(configGimnasio.getName()).get());

        detailsGimnasio.add(detailGimnasioCemento);
        detailsGimnasio.add(detailGimnasioGrava);
        detailsGimnasio.add(detailGimnasioArena);
        detailsGimnasio.add(detailGimnasioMadera);
        detailsGimnasio.add(detailGimnasioAdobe);

        constructionConfigDetailRepository.saveAll(detailsGimnasio);


    }
}
