package med.voll.api.domain.consulta.validacoes;

import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.medico.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorMedicoComOutraConsultaMesmoHorario implements ValidadorAgendamentoConsulta {

    @Autowired
    private MedicoRepository medicoRepository;

    public void validar(DadosAgendamentoConsulta dados) {
        var medicoEstaComOutraConsulta = medicoRepository.existsByMedicoIdAndData(dados.idMedico(), dados.data());
    }

}
