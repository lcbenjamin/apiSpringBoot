package med.voll.api.domain.consulta.validacoes;

import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.infra.exception.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorMedicoComOutraConsultaMesmoHorario implements ValidadorAgendamentoConsulta {

    @Autowired
    private ConsultaRepository repository;

    public void validar(DadosAgendamentoConsulta dados) {
        var medicoEstaComOutraConsulta = repository.existsByMedicoIdAndData(dados.idMedico(), dados.data());
        if (medicoEstaComOutraConsulta) {
            throw new ValidacaoException("Medico já está com outra consulta agendada nesse horário");
        }
    }

}
