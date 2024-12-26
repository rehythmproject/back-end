//package com.example.redunm;
//
//import com.example.redunm.modellist.DataModel;
//import com.example.redunm.modellist.DataModelRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class InitializeModels {
//
//    @Autowired
//    private DataModelRepository dataModelRepository;
//
//    public void initializeModels() {
//        dataModelRepository.save(new DataModel("bigyo", "BIG-01", 100, "회의의 문제점을 찾아서 피드백을 제공해 줍니다."));
//        dataModelRepository.save(new DataModel("delLa", "DEL-02", 150, "회의내용들을 분석해서 회의의 방향성, 목적, 추가로 필요한 기술등의 솔루션을 제공합니다."));
//        dataModelRepository.save(new DataModel("toron", "TOR-03", 120, "회사내에서 토론이나 개인적인 토론시 그의 기반되는 근거자료들을 찾아줍니다."));
//        dataModelRepository.save(new DataModel("barum", "BAR-04", 140, "음성인식을 통해 제2외국어등 발음 교정을 해줍니다."));
//        dataModelRepository.save(new DataModel("dobi", "DOB-05", 130, "기술개발등을 할 때 그에 맞는 로드맵과 계획 날짜 등을 정리해 줍니다."));
//        dataModelRepository.save(new DataModel("yoyak", "YOY-06", 160, "긴 회의 시 회의 내용을 효율적이게 정리해줍니다."));
//    }
//}
