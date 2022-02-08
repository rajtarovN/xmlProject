package com.example.demo.service;

import com.example.demo.dto.SaglasnostDTO;
import com.example.demo.model.obrazac_saglasnosti_za_imunizaciju.Saglasnost;
import com.example.demo.repository.SaglasnostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.modules.XMLResource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SaglasnostService {

    @Autowired
    private SaglasnostRepository saglasnostRepository;

    public ArrayList<SaglasnostDTO> pretragaTermina(String imePrezime, Date datumTermina) {
        List<String> ids = new ArrayList<>();
        if(datumTermina == null){
            datumTermina = new Date();
        }
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");

        try {
            ids = this.saglasnostRepository.pretragaTermina(imePrezime, ft.format(datumTermina));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        ids = (ArrayList<String>) ids;

        ArrayList<SaglasnostDTO> lista = new ArrayList<>();

        for(String i : ids) {
            Saglasnost z = this.pronadjiZalbuPoId(Long.parseLong(i));
            lista.add(new SaglasnostDTO(z));
        }

        return lista;

    }

    public Saglasnost pronadjiZalbuPoId(long id) {
        ResourceSet set = this.saglasnostRepository.pronadjiPoId(id);
        try {
            if (set.getSize() == 1) {

                JAXBContext context = JAXBContext.newInstance("com.example.demo.model.obrazac_saglasnosti_za_imunizaciju");

                Unmarshaller unmarshaller = context.createUnmarshaller();
                Resource res = set.getResource(0);

                Saglasnost s = (Saglasnost) unmarshaller
                        .unmarshal(((XMLResource) res).getContentAsDOM());

                return s;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }

    }
}
