//package com.example.redunm;
//
//import com.example.redunm.modellist.DataModel;
//import com.example.redunm.modellist.ModelRepository;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class DataInitializer {
//
//    @Bean
//    public CommandLineRunner initData(ModelRepository modelRepository) {
//        return args -> {
//            // 회의
//            modelRepository.save(new DataModel("delLa", "회의", 150));
//            modelRepository.save(new DataModel("yoyak", "회의", 150));
//            modelRepository.save(new DataModel("bigyo", "회의", 150));
//
//            // 업무
//            modelRepository.save(new DataModel("toron", "업무", 150));
//            modelRepository.save(new DataModel("dobi", "업무", 150));
//
//            // 공부
//            modelRepository.save(new DataModel("barum", "공부", 150));
//        };
//    }
//}
