package br.site.necrohunter.controllers;

public interface EnviarEmailIF {

	public void sendMail(String from, String to, String subject, String message);
}
