/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atalaia.inventario.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 *
 * @author alex
 */
public class InfoSO {
    public static String getNomeHost() {
        try {
            return InetAddress.getLocalHost().getCanonicalHostName();
        } catch (UnknownHostException e) {
            return "Desconhecido"; 
        }
    }
    
    public static String getEnderecoHost() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            return "Desconhecido"; 
        }
    }
    
    public static String getNomeUsuario() {
        return System.getProperty("user.name");
    }
    
    public static String getScriptUsuario(){
        return System.getProperty("user.script");
    }
    
    public static String getPaisUsuario(){
        return System.getProperty("user.country");
    }
    
    public static String getPastaAplicacao(){
        return System.getProperty("user.dir");
    }
    
    public static String getPastaUsuario(){
        return System.getProperty("user.home");
    }
    
    public static String getPastaDesktop(){
        return System.getProperty("user.home")+"\\Desktop";
    }
    
    public static String getLinguagemUsuario(){
        return System.getProperty("user.language");
    }
    
    public static String getNomeSO() {
        return System.getProperty("os.name");
    }
    
    public static String getArquiteturaSO() {
        return System.getProperty("os.arch");
    }
    
    public static String getVersaoSO() {
        return System.getProperty("os.version");
    }
    
    public static String getNomeJava(){
        return System.getProperty("java.runtime.name");
    }
    
    public static String getVersaoJava(){
        return System.getProperty("java.vm.version");
    }
    
    public static String getNomeVendedorJava(){
        return System.getProperty("java.vm.vendor");
    }
    
    public static String getSiteVendedorJava(){
        return System.getProperty("java.vendor.url");
    }
    
    public static String getNomeJavaVM(){
        return System.getProperty("java.vm.name");
    }
    
    public static String getNomeEspecificoJavaVM(){
        return System.getProperty("java.vm.specification.name");
    }
    
    public static String getVersaoJavaRunTime(){
        return System.getProperty("java.runtime.version");
    }
    
    public static String getGraficoJava(){
        return System.getProperty("java.awt.graphicsenv");
    }
    
    public static String getPastaTemporaria(){
        return System.getProperty("java.io.tmpdir");
    }
    
    public static String getNomeEspecificoVendedorJava(){
        return System.getProperty("java.vm.specification.vendor");
    }
    
    public static String getPastaBibliotecaJava(){
        return System.getProperty("java.library.path");
    }
    
    public static String getNomeEspecificoJava(){
        return System.getProperty("java.specification.name");
    }
    
    public static String getVersaoClasseJava(){
        return System.getProperty("java.class.version");
    }
    
    public static String getPastaPrincipalJava(){
        return System.getProperty("java.home");
    }
    
    public static String getVersaoEspecificaJava(){
        return System.getProperty("java.specification.version");
    }
    
    public static String getPastaClasseAplicacao(){
        return System.getProperty("java.class.path");
    }
    
    public static String getVersaoEspecificaJavaVM(){
        return System.getProperty("java.vm.specification.version");
    }
    
    public static String getSeparadorPastaSO(){
        return System.getProperty("path.separator");
    }
    
    public static String getSeparadorLinhaSO(){
        return System.getProperty("line.separator");
    }
    
    public static String getPacoteCodificadorArquivo(){
        return System.getProperty("file.encoding.pkg");
    }
    
    public static String getSeparadorArquivo(){
        return System.getProperty("file.separator");
    }
    
    public static String getCodificadorArquivo(){
        return System.getProperty("file.encoding");
    }
    
    public static String getPastaBibliotecaInicialJava(){
        return System.getProperty("sun.boot.library.path");
    }
    
    public static String getInicializadorJava(){
        return System.getProperty("sun.java.launcher");
    }
    
    public static String getNivelSOJava(){
        return System.getProperty("sun.os.patch.level");
    }
    
    public static String getGerenciadorCompilacaoJava(){
        return System.getProperty("sun.management.compiler");
    }
    
    public static String getArquiteturaJava(){
        return System.getProperty("sun.arch.data.model");
    }
    
    public static String getOrigemComandoJava(){
        return System.getProperty("sun.java.command");
    }
    
    public static String getNomeSo(){
        return System.getProperty("sun.desktop");
    }
    
    public static String getNomeCPUListaISA(){
        return System.getProperty("sun.cpu.isalist");
    }
    
    public static ArrayList<String> imprimeTudo(){
        ArrayList<String> dados = new ArrayList<>();
        
        dados.add("---------Dados usuário");
        dados.add("Nome do usuário: "+getNomeUsuario());
        dados.add("Língua do usuário: "+getLinguagemUsuario());
        dados.add("País do usuário: "+getPaisUsuario());
        
        dados.add("---------Dados técnicos usuário");
        dados.add("Pasta principal do usuário: "+getPastaUsuario());
        dados.add("Pasta desktop do usuário: "+getPastaDesktop());
        dados.add("Script usuário: "+getScriptUsuario());
        
        dados.add("---------Dados computador");
        dados.add("Nome do S.O.: "+getNomeSo());
        dados.add("Endereço do computador: "+getEnderecoHost());
        dados.add("Nome host: "+getNomeHost());
        dados.add("Nome SO: "+getNomeSO());
        dados.add("Versão SO: "+getVersaoSO());
        dados.add("Pasta temporária: "+getPastaTemporaria());
        
        dados.add("---------Dados técnicos computador");
        dados.add("Nome CPU lista ISA: "+getNomeCPUListaISA());
        dados.add("Separador de arquivos: "+getSeparadorArquivo());
        dados.add("Separador de linha: "+getSeparadorLinhaSO());
        dados.add("  |_ char[0]: "+getSeparadorLinhaSO().codePointAt(0));
        dados.add("  |_ char[1]: "+getSeparadorLinhaSO().codePointAt(1));
        dados.add("Separador de pasta: "+getSeparadorPastaSO());
        
        dados.add("---------Dados java");
        dados.add("Arquitetura de trabalho java: "+getArquiteturaJava());
        dados.add("Arquitetura de trabalho SO: "+getArquiteturaSO());
        dados.add("Nome específico do java: "+getNomeEspecificoJava());
        dados.add("Nome do java: "+getNomeJava());
        dados.add("Nome específico da java VM: "+getNomeEspecificoJavaVM());
        dados.add("Nome da java VM: "+getNomeJavaVM());
        dados.add("Pasta principal biblioteca inicial java: "+getPastaBibliotecaInicialJava());
        dados.add("Pasta principal biblioteca java: "+getPastaBibliotecaJava());
        dados.add("Pasta principal java: "+getPastaPrincipalJava());
        
        dados.add("---------Dados técnicos java");
        dados.add("Inicializador java: "+getInicializadorJava());
        dados.add("Codificador de Arquivos: "+getCodificadorArquivo());
        dados.add("Gerenciador de compilação: "+getGerenciadorCompilacaoJava());
        dados.add("Gráfico java: "+getGraficoJava());
        dados.add("Nível SO java: "+getNivelSOJava());
        dados.add("Pacote de codificação de arquivos: "+getPacoteCodificadorArquivo());
        dados.add("Nome do vendedor: "+getNomeVendedorJava());
        dados.add("Nome específico do vendedor: "+getNomeEspecificoVendedorJava());
        dados.add("Site do vendedor: "+getSiteVendedorJava());
        dados.add("Versão da classe do java: "+getVersaoClasseJava());
        dados.add("Versão específica do java: "+getVersaoEspecificaJava());
        dados.add("Versão específica do java VM: "+getVersaoEspecificaJavaVM());
        dados.add("Versão do java: "+getVersaoJava());
        dados.add("Versão da JRE: "+getVersaoJavaRunTime());
        
        dados.add("---------Dados aplicação");
        dados.add("Pasta da aplicação: "+getPastaAplicacao());
        dados.add("Pasta da classe da aplicação: "+getPastaClasseAplicacao());
        dados.add("Origem do comando: "+getOrigemComandoJava());
        
        return dados;
    }
    
    /*
    public static void main(String[] args){
        ArrayList<String> dados = InfoSO.imprimeTudo();
        
        for(String linha : dados){
            System.out.println(linha);
        }
    }
    */
}
