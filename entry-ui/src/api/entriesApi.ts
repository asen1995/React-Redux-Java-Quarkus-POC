import type { TableEntry } from '../types/TableEntry';

const API_URL = 'http://localhost:8080/api/entries';

export const entriesApi = {
  async getAll(): Promise<TableEntry[]> {
    const response = await fetch(API_URL);
    return response.json();
  },

  async create(entry: Omit<TableEntry, 'id'>): Promise<TableEntry> {
    const response = await fetch(API_URL, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(entry),
    });
    return response.json();
  },

  async delete(id: number): Promise<void> {
    await fetch(`${API_URL}/${id}`, { method: 'DELETE' });
  },
};
