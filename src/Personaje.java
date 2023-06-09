
/**
 * Esta clase modela un personaje de un juego de rol.
 */
public class Personaje {
    private final Integer MAX_VIDA;
    private final Integer PESO_MAXIMO_BOLSA;
    // nombre -> el nombre del personaje
    private String nombre;
    // vida -> valor actual de vida
    private Integer vida;
    // caldero -> el caldero para preparar pocimas
    private Caldero caldero;
    // objeto -> el elemento que tiene en sus manos
    //           Puede tener las manos vacias (null)
    private Elemento objeto;
    // bolsa -> la Bolsa de elementos
    private Bolsa bolsa;

    /**
     * Crea un personaje con el nombre dado y configura los
     * campos de la superclase: vida, MAX_VIDA y PESO_MAXIMO_BOLSA.
     * 
     * El personaje se inicializa sin bolsa, sin caldero y
     * con las manos vacias.
     * 
     * @param nombre El nombre del personje.
     * @param vida El valor inicial y maximo de vida del personaje.
     * @param peso El peso maximo que puede transportar el personaje.
     */
    public Personaje (String nombre, Integer vida, Integer peso) {
        this.nombre = nombre;
        this.vida = vida;
        this.MAX_VIDA = vida;
        this.PESO_MAXIMO_BOLSA = peso;
        this.objeto = null;
        this.bolsa = null;
    }

    /**
     * Establece la nueva bolsa para el personaje.
     * 
     * Solo se puede asignar bolsas cuyo peso maximo de carga
     * no supere el maximo soportado por el personaje.  Si el
     * personaje ya tiene una bolsa asociada, la nueva bolsa
     * debe tener mayor capacidad que la actual.  Si no se
     * cumple algunos de los requisito se debe imprimir:
     *         "Bolsa inapropiada"
     * 
     * En caso de reemplazar la bolsa actual, deben pasarse
     * todos los objetos a la nueva bolsa.
     * 
     * @param bolsa La nueva bolsa del personaje.
     */
    public void setBolsa(Bolsa bolsa) {
        if(bolsa.getPesoMaximo() <= this.PESO_MAXIMO_BOLSA) {
            if(this.bolsa != null) {
                if(this.bolsa.getPesoMaximo() < bolsa.getPesoMaximo()) {
                    for (Elemento elementoEnLaBolsaAntigua: this.bolsa.getElementosEnLaBolsa()) {
                        bolsa.addElemento(elementoEnLaBolsaAntigua);
                    }
                    this.bolsa = bolsa;
                } else {
                    System.out.println("Bolsa inapropiada");
                }
            } else {
                this.bolsa = bolsa;
            }
        } else {
            System.out.println("Bolsa inapropiada");
        }

    }

    /**
     * Devuelve la bolsa del personaje.
     * 
     * @return La bolsa del personaje.
     */
    public Bolsa getBolsa() {
        return bolsa;
    }

    /**
     * Guarda el elemento que tiene en sus manos en la bolsa, siempre
     * y cuando haya lugar suficiente.  Las manos quedan vacias (null).
     * 
     * Si las manos estan vacias (null), mostrar "No hay elemento
     * para agregar a la bolsa"
     */
    public void guardarElemento() {
        if(
            this.objeto != null &&
            this.getBolsa() != null &&
            this.getBolsa().getPesoLibre() >= this.objeto.getPeso()
        ) {
            this.getBolsa().addElemento(this.objeto);
            this.objeto = null;
        } else {
            System.out.println("No hay elemento para agregar a la bolsa");
        }
    }

    /**
     * Toma un elemento de la bolsa (delElemento) y lo pone
     * en las manos del personaje.
     * 
     * Si no existe el elemento se debe imprimir
     *       "No se cuenta con el <nombre>"
     * donde <nombre> es el nombre del elemento buscado.
     * 
     * @param nombre El elemento a tomar de la bolsa.
     */
    public void tomarElemento (String nombre) {
        Elemento elementoTomado = this.bolsa.delElemento(nombre);
        if(elementoTomado != null) {
            this.objeto = elementoTomado;
        } else {
            System.out.println("No se cuenta con el " + nombre);
        }

    }
    
    /**
     * Asigna el caldero al personaje.
     * 
     * @param caldero El caldero para el personaje.
     */
    public void setCaldero(Caldero caldero) {
        this.caldero = caldero;
    }
    
    /**
     * Devuelve el caldero que posee el personaje.
     * 
     * @return El caldero que posee el personaje.
     */
    public Caldero getCaldero() {
        return caldero;
    }

    /**
     * Prepara la pocima de la receta en el caldero.
     * 
     * Se debe agregar la receta al caldero (setReceta)
     * y luego, tomar de la bolsa los ingredientes.
     * 
     * En caso de no contar con todos los ingredientes se
     * debe imprimir:
     *       "Faltan <n> ingredientes para <receta>"
     * donde <n> es la cantidad de ingredientes faltantes y
     * <receta> es el nombre de la receta.
     * 
     * @param receta
     */
    public void prepararReceta (Receta receta) {
        int elementosFaltanterCounter = 0;

        this.getCaldero().setReceta(receta);
        for (String ingredientesParaLaRecetaIterator : this.getCaldero().getIngredientesFaltantes()) {
            Elemento ingredienteParaLaReceta = this.getBolsa().delElemento(ingredientesParaLaRecetaIterator);
        
            if (ingredienteParaLaReceta != null) {
                this.getCaldero().addIngrediente(ingredienteParaLaReceta);
            } else {
                elementosFaltanterCounter += 1;
            }
        }

        if(elementosFaltanterCounter > 0) {
            System.out.println("Faltan " + elementosFaltanterCounter + " ingredientes para " + receta.getNombre());
        } else {
            this.getCaldero().prepararPocima();
        }
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getVida() {
        return vida;
    }

    public void setVida(Integer vida) {
        this.vida = vida;
    }

    public void resetVida(Integer vida) {
        this.vida = MAX_VIDA;
    }

    public Elemento getElemento () {
        return objeto;
    }

    public void setElemento (Elemento objeto) {
        this.objeto = objeto;
    }

}
