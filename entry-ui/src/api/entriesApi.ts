import type { TableEntry, CreateTableEntry, PagedResponse } from '../types/TableEntry';

const API_URL = 'http://localhost:8080/api/entries';

export const entriesApi = {
  async getAll(
    page = 0,
    size = 10,
    sortBy = 'createdAt',
    sortDirection = 'desc'
  ): Promise<PagedResponse<TableEntry>> {
    const response = await fetch(
      `${API_URL}?page=${page}&size=${size}&sortBy=${sortBy}&sortDirection=${sortDirection}`
    );
    return response.json();
  },

  async create(entry: CreateTableEntry): Promise<TableEntry> {
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
