/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.fei.sgpa.domain.plandecurso;

/**
 *
 * @author beto
 */
public class Bibliografia {
    
        String autor;
        String tituloDelLibro;
        String Editorial;
        int año;
        
    public Bibliografia(){
    
    }

    public String getAutor() {
        return autor;
    }

    public String getTituloDelLibro() {
        return tituloDelLibro;
    }

    public String getEditorial() {
        return Editorial;
    }

    public int getAño() {
        return año;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public void setTituloDelLibro(String tituloDelLibro) {
        this.tituloDelLibro = tituloDelLibro;
    }

    public void setEditorial(String Editorial) {
        this.Editorial = Editorial;
    }

    public void setAño(int año) {
        this.año = año;
    }
    
    
}
