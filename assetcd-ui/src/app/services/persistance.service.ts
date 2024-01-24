import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class PersistanceService {

  constructor() { }

  setToLocal(key: string, data: any): void {
    try {
      localStorage.setItem(key, JSON.stringify(data));
    } catch (e) {
      console.error('Error saving to localStorage', e);
    }
  }

  getFromLocal(key : string) {
    try {
      return JSON.parse(localStorage.getItem(key) || '{}');
    } catch (e) {
      console.error('Error getting data from localStorage', e);
      return null;
    }
  }

  removeFromLocal(key: string) {
    try {
      localStorage.removeItem(key);
    } catch (e) {
      console.error('Error removing data from localStorage', e);
    }
  }

  setToSession(key: string, data: any): void {
    try {
      sessionStorage.setItem(key, JSON.stringify(data));
    } catch (e) {
      console.error('Error saving to sessionStorage', e);
    }
  }

  getFromSession(key: string) {
    try {
      return JSON.parse(sessionStorage.getItem(key) || '{}');
    } catch (e) {
      console.error('Error getting data from sessionStorage', e);
      return null;
    }
  }

  removeFromSession(key: string) {
    try {
      sessionStorage.removeItem(key);
    } catch (e) {
      console.error('Error removing data from sessionStorage', e);
    }
  }

}
