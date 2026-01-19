import { useState } from 'react';
import {
  Box,
  TextField,
  Button,
  MenuItem,
  Select,
  FormControl,
  InputLabel,
} from '@mui/material';
import type { SelectChangeEvent } from '@mui/material';
import { useAppDispatch } from '../store/hooks';
import { addEntry } from '../store/entriesSlice';

const SELECTOR_OPTIONS = ['Option A', 'Option B', 'Option C'];

export function EntryForm() {
  const dispatch = useAppDispatch();
  const [numberValue, setNumberValue] = useState('');
  const [selectorValue, setSelectorValue] = useState('');
  const [freeText, setFreeText] = useState('');

  const handleSubmit = () => {
    if (!numberValue || !selectorValue || !freeText) return;

    dispatch(
      addEntry({
        numberValue: parseInt(numberValue, 10),
        selectorValue,
        freeText,
      })
    );

    setNumberValue('');
    setSelectorValue('');
    setFreeText('');
  };

  const handleSelectorChange = (event: SelectChangeEvent) => {
    setSelectorValue(event.target.value);
  };

  return (
    <Box sx={{ display: 'flex', gap: 2, mb: 3, alignItems: 'center' }}>
      <TextField
        label="Number"
        type="number"
        value={numberValue}
        onChange={(e) => setNumberValue(e.target.value)}
        size="small"
      />
      <FormControl size="small" sx={{ minWidth: 150 }}>
        <InputLabel>Selector</InputLabel>
        <Select
          value={selectorValue}
          label="Selector"
          onChange={handleSelectorChange}
        >
          {SELECTOR_OPTIONS.map((option) => (
            <MenuItem key={option} value={option}>
              {option}
            </MenuItem>
          ))}
        </Select>
      </FormControl>
      <TextField
        label="Free Text"
        value={freeText}
        onChange={(e) => setFreeText(e.target.value)}
        size="small"
      />
      <Button variant="contained" onClick={handleSubmit}>
        Add
      </Button>
    </Box>
  );
}
