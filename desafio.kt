import java.lang.Exception
import java.time.LocalDateTime

fun main(){

    var digitalHouse = DigitalHouseManager()

    try {
        // registrarCurso = nome: String, codigoCurso: Int, quantidadeMaximaDeAlunos: Int
        digitalHouse.registrarCurso("Santander Coder Node", 1, 40)
        digitalHouse.registrarCurso("Santander Coder Java", 2, 40)
        digitalHouse.registrarCurso("Santander Coder Mobile", 3, 40)
        
        //registrarProfessorAdjunto = nome: String, sobrenome: String, codigoProfessor: Int, quantidadeDeHoras: Int
        digitalHouse.registrarProfessorAdjunto("Eduardo", "Akio", 1, 60)
        
        //registrarProfessorTitular = nome: String, sobrenome: String, codigoProfessor: Int, especialidade: String 
        digitalHouse.registrarProfessorTitular("Cesar", "Rodrigues", 1, "Android")
        
        //registrarAluno = nome: String, sobrenome: String , codigoAluno: Int
        digitalHouse.registrarAluno("Pedro", "Freitas", 6)
        
        //matricularAluno = codigoAluno: Int, codigoCurso: Int
        digitalHouse.matricularAluno(6, 3)
        
        //codigoCurso: Int, codigoProfessorTitular: Int, codigoProfessorAdjunto: Int
        digitalHouse.alocarProfessores(1, 1, 1)

    } catch (ex: Exception){
        println("Houve um problema ao cadastrar - erro $ex")
    }

    try {
        //excluirCurso =codigoCurso: Int
        digitalHouse.excluirCurso(1)
        
        //digitalHouse.excluirProfessorAdjunto(1)
        //digitalHouse.excluirProfessorTitular(1)
    } catch (err: Exception){
        println("erro: $err")
    }

}

class Aluno{
    var codigoAluno: Int? = 0
    var nome: String? = ""
    var sobrenome: String? = ""
}

abstract class Professor{
    var codigoProfessor: Int? = 0
    var nome: String? = ""
    var sobrenome: String? = ""
    var admissao: LocalDateTime = LocalDateTime.now()
}

class ProfessorTitular: Professor() {
    var especialidade: String? = ""
}

class ProfessorAdjunto: Professor(){
    var qtdeHoraMonitoria: Int? = 0
}

class Curso{
    var nomeCurso: String? = ""
    var codigoCurso: Int? = 0
    lateinit var professorTitular: ProfessorTitular
    lateinit var professorAdjunto: ProfessorAdjunto
    var qtdeMaxAluno: Int? = 0
    var listaAlunoMatriculados = mutableListOf<Aluno>()

    fun adicionarUmAluno(umAluno : Aluno) : Boolean {
        var qtdeAluno = listaAlunoMatriculados.size

        this.qtdeMaxAluno?.let{
            if(it > qtdeAluno)
            {
                listaAlunoMatriculados.add(umAluno)
                return true
            }
        }

        return false
    }

    fun excluirUmAluno(umAluno: Aluno){

        listaAlunoMatriculados.forEach{
            if(it.codigoAluno == umAluno.codigoAluno){
                listaAlunoMatriculados.remove(umAluno)
            }
        }
    }
}

class Matricula(aluno:Aluno, curso: Curso, dataMatricula: LocalDateTime = LocalDateTime.now()){
    val data = "${"%02d".format(dataMatricula.dayOfMonth)}/${"%02d".format(dataMatricula.monthValue)}/${dataMatricula.year}"
    var aluno = aluno
    var curso = curso
    var dataMatricula = data
}


class DigitalHouseManager {
    var listaAluno = mutableListOf<Aluno>()
    var listaProfessorTitular = mutableListOf<ProfessorTitular>()
    var listaProfessorAdjunto = mutableListOf<ProfessorAdjunto>()
    var listaCurso = mutableListOf<Curso>()
    var listaMatricula = mutableListOf<Matricula>()

    fun registrarCurso(nome: String, codigoCurso: Int, quantidadeMaximaDeAlunos: Int) {
        var curso = Curso()
        curso.nomeCurso = nome
        curso.codigoCurso = codigoCurso
        curso.qtdeMaxAluno = quantidadeMaximaDeAlunos
        listaCurso.add(curso)
    }
	
    fun excluirCurso(codigoCurso: Int) {
        listaCurso.forEach {
            if (it.codigoCurso == codigoCurso) {
                listaCurso.remove(it)
            }
        }
    }

    fun registrarProfessorAdjunto(nome: String, sobrenome: String, codigoProfessor: Int, quantidadeDeHoras: Int) {
        var professorAdjunto = ProfessorAdjunto()
        professorAdjunto.nome = nome
        professorAdjunto.sobrenome = sobrenome
        professorAdjunto.codigoProfessor = codigoProfessor
        professorAdjunto.qtdeHoraMonitoria = quantidadeDeHoras
        listaProfessorAdjunto.add(professorAdjunto)
    }

    fun registrarProfessorTitular(nome: String, sobrenome: String, codigoProfessor: Int, especialidade: String) {
        var professorTitular = ProfessorTitular()
        professorTitular.nome = nome
        professorTitular.sobrenome = sobrenome
        professorTitular.codigoProfessor = codigoProfessor
        professorTitular.especialidade = especialidade
        listaProfessorTitular.add(professorTitular)
    }

    fun excluirProfessorAdjunto(codigoProfessor: Int) {
        listaProfessorAdjunto.forEach {
            if (it.codigoProfessor == codigoProfessor) {
                listaProfessorAdjunto.remove(it)
            }
        }
    }

    fun excluirProfessorTitular(codigoProfessor: Int) {
        listaProfessorTitular.forEach {
            if (it.codigoProfessor == codigoProfessor) {
                listaProfessorTitular.remove(it)
            }
        }
    }

    fun registrarAluno(nome: String, sobrenome: String, codigoAluno: Int) {
        var aluno = Aluno()
        aluno.nome = nome
        aluno.sobrenome = sobrenome
        aluno.codigoAluno = codigoAluno
        listaAluno.add(aluno)
    }

    fun matricularAluno(codigoAluno: Int, codigoCurso: Int) {
        var (aluno) = listaAluno.filter { it.codigoAluno == codigoAluno }
        var (curso) = listaCurso.filter{it.codigoCurso == codigoCurso}

        if (curso.adicionarUmAluno(aluno)) {
            var matricula = Matricula(aluno, curso, LocalDateTime.now())
            listaMatricula.add(matricula)

            println("Matricula Realizada")
        } else {
            println("Ops... sem vaga :(")
        }
    }

    fun alocarProfessores(codigoCurso: Int, codigoProfessorTitular: Int, codigoProfessorAdjunto: Int) {
        var (professorTitular) = listaProfessorTitular.filter { it.codigoProfessor == codigoProfessorAdjunto }
        var (professorAdjunto) = listaProfessorAdjunto.filter { it.codigoProfessor == codigoProfessorTitular }
        var (curso) = listaCurso.filter { it.codigoCurso == codigoCurso }

        curso.professorAdjunto = professorAdjunto
        curso.professorTitular = professorTitular
        
        println("Agora o curso $curso tem o professore titular: $professorTitular e o professor adjunto $professorAdjunto")
    }    
}