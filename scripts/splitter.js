// Take in AllCards.json, in the MTG JSON format, and split it into individual
// cards, writing them out to invidividual files. using the card names as
// filenames, more or less.

'use strict';

const fs = require('fs');

process.stdin.setEncoding('utf8');

let data = '';

process.stdin.on('readable', () => {
  const chunk = process.stdin.read();
  if (chunk) {
    data += chunk;
  }
});

process.stdin.on('end', () => {
  split(data);
});

function split(jsonData) {
  const cardsByName = JSON.parse(jsonData);
  writeToFiles(Object.keys(cardsByName)
        .map((name) => {
          const content = cardsByName[name];
          const filename = makeFilename(name);
          return {filename, content};
        }));
}

function makeFilename(name) {
  const base = name.replace(/\s|[?!,"':]/g, '-')
                   .replace(/(-)+/g, '-')
                   .replace(/^-|-$/, '')
                   .toLowerCase();
  return `resources/mtgjson/${base}.json`
}

function writeToFiles(cards, cb) {
  const queue = [...cards];

  let pendingWrites = 0;
  // Need to rate limit ourselves, at least on Windows, or complains about too
  // many open files. 100 is the first number I tried, and it seems to work.
  const MAX_OPEN_FILES = 100;

  function enqueueWrites() {
    while (queue.length > 0 && pendingWrites < MAX_OPEN_FILES) {
      const card = queue.pop();
      const jsonContent = card.content;
      if (jsonContent.layout === 'token') {
        // Do not save token cards. Delete them if they are already there.
        fs.unlinkSync(card.filename);
      } else {
        ++pendingWrites;
        fs.writeFile(card.filename, JSON.stringify(card.content, undefined, 2),
                     handleWriteCompletion);
      }
    }
  }

  function handleWriteCompletion(err) {
    --pendingWrites;
    if (err) {
      throw err;
    }
    if (!queue.length && !pendingWrites) {
      console.log(`Done writing ${cards.length} cards.`);
    } else {
      enqueueWrites();
    }
  }

  enqueueWrites();
}
