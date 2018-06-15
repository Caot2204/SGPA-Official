/****************************************************************/
/* Nombre: Carlos Alberto Onorio Torres.			  */
/* Fecha de creación:   16/04/2018				  */
/* Ultima modificación: 18/05/2018				  */
/* Descripción: Posibles estados que puede tener un Formato de    */
/*              Academia.                                         */
/****************************************************************/

package mx.fei.sgpa.domain;

/**
 * Posibles estados para un Formato de Academia. EN_EDICION permite que el Formato
 * de Academia pueda ser editado indefinidas veces, CONCLUIDO solo permite visualizar
 * el Formato de Academia pero sin modificarlo.
 */
public enum EstadoDeDocumento{
    EN_EDICION, CONCLUIDO; 
}